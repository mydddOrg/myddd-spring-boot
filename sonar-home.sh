#!/bin/sh

./gradlew test jacocoTestReport && echo "测试成功" || exit 1
./gradlew sonarqube \
  -Dsonar.projectKey=myddd-java \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=ba7888fa58e99000136456095c892eea26759ab7