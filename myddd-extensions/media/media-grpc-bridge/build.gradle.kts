plugins {
    `java-library`
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation("javax.inject:javax.inject:1")

    implementation(project(":myddd-extensions:media:media-api"))
    implementation(project(":myddd-utils"))
    implementation(project(":myddd-ioc:myddd-ioc-api"))

    implementation(project(":myddd-extensions:media:media-api"))

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")
}

sonarqube {
    isSkipProject = true
}