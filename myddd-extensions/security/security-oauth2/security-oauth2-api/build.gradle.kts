plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
}

sonarqube {
    isSkipProject = true
}