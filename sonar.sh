#!/bin/sh

token="${SONAR_TOKEN}"

if [ "$token" == "" ]; then
   echo "没有读取到SONAR_TOKEN系统变量"
   exit 1

fi

./gradlew test && echo "测试成功" || exit 2
./gradlew sonar \
  -Dsonar.projectKey=myddd-spring-boot \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_76a6f09c18fc51956e6c74bf6b2672e399b2e0dc