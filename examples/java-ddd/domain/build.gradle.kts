plugins {
    `java-library`
}

group = "org.myddd.java"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-domain"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))

    testImplementation(project(":examples:java-ddd:infra"))
}

sonarqube {
    isSkipProject = true
}