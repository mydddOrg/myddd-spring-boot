plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.organisation"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":myddd-extensions:organisation:organisation-api"))
    implementation(project(":myddd-extensions:organisation:organisation-domain"))
    implementation(project(":myddd-extensions:security:security-api"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))

    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")

    testImplementation(project(":myddd-extensions:organisation:organisation-infra"))
    testImplementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")

}