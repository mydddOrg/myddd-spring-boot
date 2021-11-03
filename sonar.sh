#!/bin/sh

type=$1

if [ "$type" == "office" ]; then
    token="a1de27b97e33f4f1c7373373583907cc30672c11"
elif [ "$type" == "home" ]; then
    token="ba7888fa58e99000136456095c892eea26759ab7"
else
   echo "命令执行有误，请指定环境，'sh sonar.sh home' 或者 'sh sonar.sh office'"
   exit 1
fi

echo $token

./gradlew test && echo "测试成功" || exit 2
./gradlew sonarqube \
  -Dsonar.projectKey=myddd-java \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=$token