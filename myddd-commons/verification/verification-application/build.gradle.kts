plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!


dependencies {
    api(project(":myddd-commons:verification:verification-api"))

    implementation(project(":myddd-lang"))
    api(project(":myddd-ioc:myddd-ioc-api"))

    api("org.springframework.boot:spring-boot-starter:${rootProject.extra["spring.boot"]}")

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}