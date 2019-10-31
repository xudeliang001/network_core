#!/usr/bin/env bash
java  -XX:+UseG1GC \
       -server \
       -Duser.timezone='GMT+08:00' \
       -Duser.country=CN \
       -Duser.language=zh \
       -Djava.security.egd=file:/dev/./urandom \
       -XX:+UnlockExperimentalVMOptions \
       -XX:+UseCGroupMemoryLimitForHeap \
       -XX:MaxRAMFraction=2 \
       -XX:HeapDumpPath=/opt/uca-network-core/log/ \
       -XX:-HeapDumpOnOutOfMemoryError \
       -Xdebug \
       -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=30460 \
       -jar uca-network-core.jar
