plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api(project(":myddd-utils"))
    implementation("javax.inject:javax.inject:1")
    implementation("org.eclipse.persistence:javax.persistence:2.2.1")
}
