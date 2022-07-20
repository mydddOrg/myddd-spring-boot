plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-domain"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")
    testImplementation(project(":myddd-extensions:security:security-infra"))
}
