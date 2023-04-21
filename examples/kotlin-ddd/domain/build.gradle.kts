plugins {
    kotlin("jvm")
    idea
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-domain"))

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation(project(":examples:kotlin-ddd:infra"))
}

sonar {
    isSkipProject = true
}