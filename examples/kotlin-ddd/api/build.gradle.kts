plugins {
    kotlin("jvm")
    idea
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-utils"))
}

sonarqube {
    isSkipProject = true
}