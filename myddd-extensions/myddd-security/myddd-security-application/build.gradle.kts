plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:myddd-security:myddd-security-api"))
    implementation(project(":myddd-extensions:myddd-security:myddd-security-domain"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))

    testImplementation(project(":myddd-extensions:myddd-security:myddd-security-infra"))
}