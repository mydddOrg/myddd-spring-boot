plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api(project(":myddd-lang"))
    api("javax.inject:javax.inject:1")
}

sonarqube {
    isSkipProject = true
}