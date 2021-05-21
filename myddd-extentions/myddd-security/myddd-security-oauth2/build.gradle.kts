import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    `java-library`
    id("org.springframework.boot")
}

apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-domain"))

    implementation("org.springframework.security.oauth:spring-security-oauth2:2.4.1.RELEASE")
    implementation("org.apache.tomcat.embed:tomcat-embed-core")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-core")

    implementation(project(":myddd-extentions:myddd-security:myddd-security-api"))

    implementation("javax.inject:javax.inject:1")
    implementation("org.eclipse.persistence:javax.persistence:2.2.1")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}