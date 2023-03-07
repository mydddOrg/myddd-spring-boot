plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api(project(":myddd-lang"))
}

sonarqube {
    isSkipProject = true
}