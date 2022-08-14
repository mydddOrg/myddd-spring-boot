plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.organisation"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation("javax.inject:javax.inject:1")

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-ioc:myddd-ioc-api"))

    implementation(project(":myddd-extensions:organisation:organisation-api"))


    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")
}

sonarqube {
    isSkipProject = true
}
