plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-commons:verification:verification-application"))
    api("javax.inject:javax.inject:1")
}