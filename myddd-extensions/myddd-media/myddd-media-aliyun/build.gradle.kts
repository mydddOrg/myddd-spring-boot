plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!


extra["aliyun.sdk.version"] = "3.8.0"

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-extensions:myddd-media:myddd-media-api"))
    implementation("com.aliyun.oss:aliyun-sdk-oss:${project.extra["aliyun.sdk.version"]}")

    implementation("commons-logging:commons-logging:1.2")
    implementation("commons-io:commons-io:2.7")

}

sonarqube {
    isSkipProject = true
}