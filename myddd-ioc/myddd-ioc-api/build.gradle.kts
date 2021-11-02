plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-utils"))
    api("javax.inject:javax.inject:1")
}
