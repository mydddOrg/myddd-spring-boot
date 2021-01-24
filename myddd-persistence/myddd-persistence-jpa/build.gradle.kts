import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    `java-library`
    id("org.springframework.boot")
}

apply(plugin = "io.spring.dependency-management")

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

dependencies {
    api(project(":myddd-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra["spring.boot"]}")
    implementation("org.eclipse.persistence:javax.persistence:2.2.1")
    implementation("javax.inject:javax.inject:1")
}