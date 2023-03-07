plugins {
    kotlin("jvm")
    idea
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":examples:kotlin-ddd:domain"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
}
