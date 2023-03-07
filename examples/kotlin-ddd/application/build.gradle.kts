plugins {
    kotlin("jvm")
    idea
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":examples:kotlin-ddd:api"))
    implementation(project(":examples:kotlin-ddd:domain"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))

    testImplementation(project(":examples:kotlin-ddd:infra"))

}

sonarqube {
    isSkipProject = true
}