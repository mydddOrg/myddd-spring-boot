plugins {
    `java-library`
}

group = "org.myddd.java"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-utils"))
}

sonar {
    isSkipProject = true
}