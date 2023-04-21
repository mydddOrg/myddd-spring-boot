plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.verification"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    implementation("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    implementation(project(":myddd-commons:verification:verification-application"))

    implementation("javax.mail:javax.mail-api:1.6.2")
    implementation("com.sun.mail:javax.mail:1.6.2")

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}

sonarqube {
    isSkipProject = true
}