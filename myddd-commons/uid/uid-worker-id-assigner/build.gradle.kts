plugins {
    `java-library`
    id("idea")
}

group = "org.myddd.commons"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-domain"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}
