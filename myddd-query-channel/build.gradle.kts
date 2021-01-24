import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    `java-library`
    id("org.springframework.boot")
}

apply(plugin = "org.springframework.boot")
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
    api("org.eclipse.persistence:javax.persistence:2.2.1")
    api("javax.inject:javax.inject:1")

}