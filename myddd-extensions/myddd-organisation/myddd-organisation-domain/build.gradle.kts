plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.organisation"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-domain"))

    implementation(project(":myddd-extensions:myddd-security:myddd-security-api"))
    implementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation(project(":myddd-extensions:myddd-organisation:myddd-organisation-infra"))
    testImplementation(project(":myddd-extensions:myddd-security:myddd-security-api"))

}
