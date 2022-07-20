plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

extra["aliyun.sdk.version"] = "3.13.0"

dependencies {
    implementation(project(":myddd-extensions:media:media-domain"))
    implementation(project(":myddd-lang"))

    implementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")
    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")
    implementation("com.aliyun.oss:aliyun-sdk-oss:${project.extra["aliyun.sdk.version"]}")
    implementation("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    implementation("org.springframework.boot:spring-boot-starter:${rootProject.extra["spring.boot"]}")


    testImplementation(project(":myddd-domain"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))

}

sonarqube {
    isSkipProject = true
}