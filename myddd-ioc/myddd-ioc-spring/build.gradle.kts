import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
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
    implementation(project(":myddd-domain"))
    implementation("org.springframework:spring-context")
}
