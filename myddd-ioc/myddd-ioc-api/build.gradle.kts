plugins {
    `java-library`
    idea
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-utils"))
    api("jakarta.inject:jakarta.inject-api:${rootProject.extra["jakarta_inject_version"]}")
}
