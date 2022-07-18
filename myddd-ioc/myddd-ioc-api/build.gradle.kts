plugins {
    `java-library`
    idea
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-utils"))
    api("javax.inject:javax.inject:${rootProject.extra["javax_inject_version"]}")
}
