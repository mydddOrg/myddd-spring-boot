plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:security:security-api"))
    implementation(project(":myddd-extensions:security:security-domain"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))

    testImplementation(project(":myddd-extensions:security:security-infra"))
    testImplementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")
}