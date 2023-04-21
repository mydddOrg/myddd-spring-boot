plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    api(project(":myddd-lang"))
}

sonarqube {
    isSkipProject = true
}