#!/bin/bash

/opt/bin/kubectl --server=127.0.0.1:8888 create -f /tmp/uca-network-core/uca-network-core/yaml/confFile-cluster/uca-network-core-service.yaml
/opt/bin/kubectl --server=127.0.0.1:8888 create -f  /tmp/uca-network-core/uca-network-core/yaml/confFile-cluster/uca-network-core-rc.yaml
