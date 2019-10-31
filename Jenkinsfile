pipeline {
    agent {
         docker {
             image env.DOCKER_FARM_IMAGE
             label env.DOCKER_FARM_LABEL
             args  env.DOCKER_FARM_ARGS
         }
    }
    options {
        gitLabConnection(env.gitlabConnection)
        gitlabBuilds(builds: ['checkout', 'clean', 'p3c-check', 'compile',  'test'])
        // ansiColor('xterm')
        timestamps()
    }
    environment{
          SERVICE_NAME='uca-network-core'
          RESOURCES_LIMITS_CPU =  '2'
          RESOURCES_LIMITS_MEMORY = '2048Mi'
          RESOURCES_REQUESTS_CPU = '0.2'
          RESOURCES_REQUESTS_MEMORY = '1536Mi'
          LIVENESSPROBE_PORT = '8080'
          LIVENESSPROBE_INITIALDELAYSECONDES = '60'
          LIVENESSPROBE_TIMEOUTSECONDS = '10'
          LIVENESSPROBE_SUCCESSTHRESHOLD = '1'
          LIVENESSPROBE_FAILURETHRESHOLD = '5'
          READINESSPROBE_PORT = '8081'
          READINESSPROBE_INITIALDELAYSECONDES = '3'
          READINESSPROBE_TIMEOUTSECONDS = '5'
          PCO_REPLICAS='3'


//        SERVICE_GROUP='h3cloud/pco'
//        DOCKER_NODE_PORT='8088'
    }

    post {
        always { step([$class: 'WsCleanup']) }
        success {
            addGitLabMRComment comment: ':white_check_mark: Jenkins Build SUCCESS\n\n' +
                    "Results available at: [Jenkins [${env.JOB_BASE_NAME} ${env.BUILD_DISPLAY_NAME}]](${env.BUILD_URL})"
        }
        failure {
            addGitLabMRComment comment: ':negative_squared_cross_mark: Jenkins Build Failure\n\n' +
                    "Results available at: [Jenkins [${env.JOB_BASE_NAME} ${env.BUILD_DISPLAY_NAME}]](${env.BUILD_URL})"
        }
    }
    stages {
        stage('checkout') {
            post {
                success { updateGitlabCommitStatus name: 'checkout', state: 'success' }
                failure { updateGitlabCommitStatus name: 'checkout', state: 'failed' }
            }
            steps {
                script{
                    checkoutDependOnEnv env
                }
            }
        }
        stage('clean') {
            post {
                success { updateGitlabCommitStatus name: 'clean', state: 'success' }
                failure { updateGitlabCommitStatus name: 'clean', state: 'failed'  }
            }
            steps {
                //sh """
                //    git clean -fxd
                //"""
                sh 'gradle --no-daemon clean'
                //sh "docker images| grep ${SERVICE_NAME}|awk '{print \$3}'|xargs docker rmi || true"
            }
        }
        stage('p3c-check') {
            post {
                always{
                    updateGitlabCommitStatus name: 'p3c-check', state: 'success'
                }
            }
            steps {
                script{
                    try{
                        sh "${P3C_PMD_HOME}/bin/pmd-check.sh src/main/java build/reports/pmd"
                    }catch(Throwable e){
                        currentBuild.result = 'SUCCESS'
                    }
                }
                pmd canComputeNew: false, canRunOnFailed: true, defaultEncoding: '', healthy: '', pattern: 'build/reports/pmd/main.xml', shouldDetectModules: true, unHealthy: ''
            }
        }
        stage('compile') {
            post {
                success {
                    updateGitlabCommitStatus name: 'compile', state: 'success'
                }
                failure {
                    updateGitlabCommitStatus name: 'compile', state: 'failed'
                }
            }
            steps {
                sh 'gradle --no-daemon build --stacktrace'
            }
        }

        stage('docker') {

            post {
                success { updateGitlabCommitStatus name: 'docker', state: 'success'  }
                failure { updateGitlabCommitStatus name: 'docker', state: 'failed'  }
            }
            steps {
                script{
                    sh """
                        docker login ${env.DOCKER_REGISTRY} -u ${env.DOCKER_REGISTRY_USER} -p ${env.DOCKER_REGISTRY_PWD}
                        mkdir -p tmp/docker/${SERVICE_NAME}
                        cp build/libs/${SERVICE_NAME}*.jar tmp/docker/${SERVICE_NAME}/${SERVICE_NAME}.jar
                        cp docker/startup.sh tmp/docker/${SERVICE_NAME}/startup.sh
                        chmod +x tmp/docker/${SERVICE_NAME}/startup.sh
                        envsubst < docker/Dockerfile.template > tmp/docker/Dockerfile
                    """
                    env.DOCKER_IMAGE = "${SERVICE_NAME}:${BUILD_VERSION}"
                    sh """
                        cd tmp/docker
                        docker build -t ${env.DOCKER_IMAGE} -f Dockerfile .
                        cd $WORKSPACE
                        mkdir -p deployment/dist/${SERVICE_NAME}/images/
                        docker save ${env.DOCKER_IMAGE} -o deployment/dist/${SERVICE_NAME}/images/${SERVICE_NAME}-${BUILD_VERSION}.tar
                    """
                }
            }
        }

        stage('package')
        {
             steps
             {
                sh """
                    cd deployment
                    mkdir -p dist/${SERVICE_NAME}/templet dist/${SERVICE_NAME}/yaml/confFile dist/${SERVICE_NAME}/yaml/confFile-cluster dist/${SERVICE_NAME}/scripts

                    envsubst < app_version.json > dist/${SERVICE_NAME}/app_version.json
                    envsubst < templet/app.json > dist/${SERVICE_NAME}/templet/app.json
                    envsubst < yaml/confFile/uca-network-core-rc.yaml > dist/${SERVICE_NAME}/yaml/confFile/uca-network-core-rc.yaml
                    envsubst < yaml/confFile/uca-network-core-service.yaml > dist/${SERVICE_NAME}/yaml/confFile/uca-network-core-service.yaml
                    envsubst < yaml/confFile-cluster/uca-network-core-rc.yaml > dist/${SERVICE_NAME}/yaml/confFile-cluster/uca-network-core-rc.yaml
                    envsubst < yaml/confFile-cluster/uca-network-core-service.yaml > dist/${SERVICE_NAME}/yaml/confFile-cluster/uca-network-core-service.yaml
                    envsubst < yaml/deploy.sh > ${WORKSPACE}/deployment/dist/${SERVICE_NAME}/yaml/deploy.sh
                    envsubst < yaml/startsvc.sh > ${WORKSPACE}/deployment/dist/${SERVICE_NAME}/yaml/startsvc.sh
                    envsubst < yaml/stopsvc.sh > ${WORKSPACE}/deployment/dist/${SERVICE_NAME}/yaml/stopsvc.sh

                    cd dist
                    tar -czvf ${SERVICE_NAME}-${BUILD_VERSION}.tar.gz ${SERVICE_NAME}
                """
                 }
        }
        stage('publish') {
            when { expression { env.BUILD_VERSION ==~ '^PUB.*'} }
            post {
                success { updateGitlabCommitStatus name: 'publish', state: 'success' }
                failure { updateGitlabCommitStatus name: 'publish', state: 'failed' }
            }
            steps {

                script {
                    String target = env.gitlabSourceRepoHttpUrl.replace("${env.GITLAB_URL}/", '')
                    target = target.replace('.git', '')
                    def uploadSpec = """{
                        "files": [{
                            "pattern": "deployment/dist/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz",
                            "target": "${env.COMPONENTS_REPO}/${target}/${BUILD_VERSION}/"
                        }]
                    }"""

                    def server = Artifactory.server(env.ARTIFACTORY_SERVER_ID)
                    def buildInfo = Artifactory.newBuildInfo()
                    server.upload(uploadSpec, buildInfo)

                    //buildInfo.env.capture = true
                    //buildInfo.env.collect()
                    //server.publishBuildInfo(buildInfo)
                }
            }
        }
         stage('deploy1'){

           when { expression { env.BUILD_VERSION ==~ '^SE.*'} }
            post {
                success { updateGitlabCommitStatus name: 'deploy', state: 'success' }
                failure { updateGitlabCommitStatus name: 'deploy', state: 'failed' }
            }
            steps{
                script{
                    //拷贝自动化脚本和tar.gz包到远程集群服务器上
                    sshagent(['pco-cluster-master']) {
                        sh """
                            scp -r   -o StrictHostKeyChecking=no  ${WORKSPACE}/deployment/dist/${SERVICE_NAME}/yaml  172.25.32.30:/tmp/pco-portal
                            scp  -o StrictHostKeyChecking=no  ${WORKSPACE}/deployment/dist/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz 172.25.32.30:/tmp/pco-portal
                        """
                    }

                    //执行部署脚本
                     sshagent(['pco-cluster-master']) {

                    sh """
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.30 rm -rf /tmp/pco-portal/pco-portal
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.30  tar axvf /tmp/pco-portal/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz -C /tmp/pco-portal
                     ssh -o StrictHostKeyChecking=no -l root  172.25.32.30 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                     ssh -o StrictHostKeyChecking=no -l root  172.25.32.30 sed -i "s/magento-service:9980/172.25.19.148:9980/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                     ssh -o StrictHostKeyChecking=no -l root  172.25.32.30 sed -i "s/api-bpm-sys-h3cloud:11860/172.25.19.148:11860/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                     ssh -o StrictHostKeyChecking=no -l root  172.25.32.30 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                     ssh -o StrictHostKeyChecking=no -l root  172.25.32.30 sed -i "s/magento-service:9980/172.25.19.148:9980/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                     ssh -o StrictHostKeyChecking=no -l root  172.25.32.30 sed -i "s/api-bpm-sys-h3cloud:11860/172.25.19.148:11860/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.30 chmod +x  /tmp/pco-portal/pco-portal/yaml/stopsvc.sh
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.30 /tmp/pco-portal/pco-portal/yaml/stopsvc.sh
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.30 chmod +x  /tmp/pco-portal/pco-portal/yaml/deploy.sh
                     ssh -o StrictHostKeyChecking=no -l root   172.25.32.30 /tmp/pco-portal/pco-portal/yaml/deploy.sh
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.30 chmod +x  /tmp/pco-portal/pco-portal/yaml/startsvc.sh
                     ssh -o StrictHostKeyChecking=no -l root   172.25.32.30 /tmp/pco-portal/pco-portal/yaml/startsvc.sh
                    """
                     }
                }
            }
        }
         stage('deploy2'){
            when { expression { env.BUILD_VERSION ==~ '^SE.*'} }
            post {
                success { updateGitlabCommitStatus name: 'deploy', state: 'success' }
                failure { updateGitlabCommitStatus name: 'deploy', state: 'failed' }
            }
            steps{
                script{
                    //拷贝自动化脚本和tar.gz包到远程集群服务器上
                    sshagent(['pco-cluster-slave1']) {
                        sh """
                            scp -r   -o StrictHostKeyChecking=no  ${WORKSPACE}/deployment/dist/${SERVICE_NAME}/yaml  172.25.32.31:/tmp/pco-portal
                            scp  -o StrictHostKeyChecking=no  ${WORKSPACE}/deployment/dist/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz 172.25.32.31:/tmp/pco-portal
                        """
                    }

                    //执行部署脚本
                     sshagent(['pco-cluster-slave1']) {

                    sh """
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.31 rm -rf /tmp/pco-portal/pco-portal
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.31  tar axvf /tmp/pco-portal/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz -C /tmp/pco-portal
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/magento-service:9980/172.25.19.148:9980/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/api-bpm-sys-h3cloud:11860/172.25.19.148:11860/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/magento-service:9980/172.25.19.148:9980/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root  172.25.32.31 sed -i "s/api-bpm-sys-h3cloud:11860/172.25.19.148:11860/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                      ssh -o StrictHostKeyChecking=no -l root 172.25.32.31 chmod +x  /tmp/pco-portal/pco-portal/yaml/stopsvc.sh
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.31 /tmp/pco-portal/pco-portal/yaml/stopsvc.sh
                      ssh -o StrictHostKeyChecking=no -l root 172.25.32.31 chmod +x  /tmp/pco-portal/pco-portal/yaml/deploy.sh
                      ssh -o StrictHostKeyChecking=no -l root   172.25.32.31 /tmp/pco-portal/pco-portal/yaml/deploy.sh
                     ssh -o StrictHostKeyChecking=no -l root 172.25.32.31 chmod +x  /tmp/pco-portal/pco-portal/yaml/startsvc.sh
                    ssh -o StrictHostKeyChecking=no -l root   172.25.32.31 /tmp/pco-portal/pco-portal/yaml/startsvc.sh
                    """
                     }
                }
            }
        }
         stage('deploy3'){
            when { expression { env.BUILD_VERSION ==~ '^SE.*'} }
            post {
                success { updateGitlabCommitStatus name: 'deploy', state: 'success' }
                failure { updateGitlabCommitStatus name: 'deploy', state: 'failed' }
            }
            steps{
                script{
                    //拷贝自动化脚本和tar.gz包到远程集群服务器上
                    sshagent(['pco-cluster-master']) {
                        sh """
                            scp -r   -o StrictHostKeyChecking=no  ${WORKSPACE}/deployment/dist/${SERVICE_NAME}/yaml  172.25.32.32:/tmp/pco-portal
                            scp  -o StrictHostKeyChecking=no  ${WORKSPACE}/deployment/dist/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz 172.25.32.32:/tmp/pco-portal
                        """
                    }

                    //执行部署脚本
                     sshagent(['pco-cluster-master']) {

                    sh """
                       ssh -o StrictHostKeyChecking=no -l root 172.25.32.32 rm -rf /tmp/pco-portal/pco-portal
                       ssh -o StrictHostKeyChecking=no -l root 172.25.32.32  tar axvf /tmp/pco-portal/${SERVICE_NAME}-${BUILD_VERSION}.tar.gz -C /tmp/pco-portal
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/magento-service:9980/172.25.19.148:9980/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/api-bpm-sys-h3cloud:11860/172.25.19.148:11860/g" /tmp/pco-portal/pco-portal/yaml/confFile/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/api-leo-sys-h3cloud/172.25.33.5/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/magento-service:9980/172.25.19.148:9980/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root  172.25.32.32 sed -i "s/api-bpm-sys-h3cloud:11860/172.25.19.148:11860/g" /tmp/pco-portal/pco-portal/yaml/confFile-cluster/uca-network-core-rc.yaml
                       ssh -o StrictHostKeyChecking=no -l root 172.25.32.32 chmod +x  /tmp/pco-portal/pco-portal/yaml/stopsvc.sh
                       ssh -o StrictHostKeyChecking=no -l root 172.25.32.32 /tmp/pco-portal/pco-portal/yaml/stopsvc.sh
                       ssh -o StrictHostKeyChecking=no -l root 172.25.32.32 chmod +x  /tmp/pco-portal/pco-portal/yaml/deploy.sh
                       ssh -o StrictHostKeyChecking=no -l root   172.25.32.32 /tmp/pco-portal/pco-portal/yaml/deploy.sh
                       ssh -o StrictHostKeyChecking=no -l root 172.25.32.32 chmod +x  /tmp/pco-portal/pco-portal/yaml/startsvc.sh
                       ssh -o StrictHostKeyChecking=no -l root   172.25.32.32 /tmp/pco-portal/pco-portal/yaml/startsvc.sh
                    """
                     }
                }
            }
        }
    }
}
