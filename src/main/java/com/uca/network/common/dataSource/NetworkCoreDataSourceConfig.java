package com.uca.network.common.dataSource;

import groovy.util.logging.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.uca.network",sqlSessionFactoryRef = "sqlSessionFactoryBeanNetworkCore")
@Slf4j
public class NetworkCoreDataSourceConfig extends DataSourceConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean(name = "dataSourceNetworkCore")
    public DataSource dataSourceNetworkCore() {
        return initDataSource("uni_network");
    }

    @Bean(name = "sqlSessionFactoryBeanNetworkCore")
    public SqlSessionFactory sqlSessionFactoryBeanNetworkCore() {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource 作为数据源则不能实现切换
        try {
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/*.xml"));
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            sqlSessionFactoryBean.setConfiguration(configuration);
            sqlSessionFactoryBean.setDataSource(dataSourceNetworkCore());
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
