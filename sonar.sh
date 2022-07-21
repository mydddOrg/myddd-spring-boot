#!/bin/sh

token="${SONAR_TOKEN}"

if [ "$token" == "" ]; then
   echo "没有读取到SONAR_TOKEN系统变量"
   exit 1

fi

./gradlew test && echo "测试成功" || exit 2
./gradlew sonarqube \
  -Dsonar.projectKey=myddd-spring-boot \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=$token