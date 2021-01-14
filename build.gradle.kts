import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "2.4.1"
}


extra["projectVersion"] = "1.1.0-SNAPSHOT"
extra["slf4jVersion"] = "1.7.30"
extra["spring.boot"] = "2.4.1"

allprojects {


    repositories {
        mavenCentral()
    }
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

group = "org.myddd"
version = extra["projectVersion"]!!


