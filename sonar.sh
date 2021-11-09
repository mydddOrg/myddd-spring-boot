#!/bin/sh

token="${SONAR_TOKEN}"

if [ "$token" == "" ]; then
   echo "没有读取到SONAR_TOKEN系统变量"
   exit 1

fi

check=0

if [ "$check" == "0" ]; then
    echo "hello"
fi

./gradlew test && echo "测试成功" || exit 2
./gradlew sonarqube \
  -Dsonar.projectKey=myddd-java \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=$token