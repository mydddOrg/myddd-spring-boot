plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-commons:verification:verification-api"))

    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    testImplementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")

    testImplementation(project(":myddd-commons:verification:verification-application"))
    testImplementation(project(":myddd-commons:verification:verification-infra:verification-gateway-mock"))
}
