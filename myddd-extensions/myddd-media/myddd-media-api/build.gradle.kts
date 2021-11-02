plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
}

sonarqube {
    isSkipProject = true
}