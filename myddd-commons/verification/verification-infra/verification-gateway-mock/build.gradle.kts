plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    implementation(project(":myddd-commons:verification:verification-application"))
}