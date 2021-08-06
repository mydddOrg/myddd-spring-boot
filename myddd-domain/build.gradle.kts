plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    api(project(":myddd-utils"))
    api("javax.inject:javax.inject:1")
    api("org.eclipse.persistence:javax.persistence:2.2.1")
}
