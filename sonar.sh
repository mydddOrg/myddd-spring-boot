#!/bin/sh

./gradlew test && echo "测试成功" || exit 1
./gradlew sonarqube \
  -Dsonar.projectKey=myddd-java \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=ba7888fa58e99000136456095c892eea26759ab7

# office
# a1de27b97e33f4f1c7373373583907cc30672c11