plugins {
    `java-library`
    id("idea")
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-commons:verification:verification-application"))
    implementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")
    api("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    testImplementation(project(":myddd-commons:cache:cache-guava"))

    implementation(project(":myddd-commons:verification:verification-application"))
}