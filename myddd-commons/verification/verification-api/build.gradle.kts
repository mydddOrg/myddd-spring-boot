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

sonar {
    isSkipProject = true
}