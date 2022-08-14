plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.organisation"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:organisation:organisation-api"))
    implementation(project(":myddd-extensions:security:security-oauth2:security-oauth2-api"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-lang"))
    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))


    implementation(project(":myddd-libs:myddd-rest-advice"))

    testImplementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")
    testImplementation(project(":myddd-extensions:security:security-api"))
    testImplementation(project(":myddd-extensions:organisation:organisation-domain"))
    testImplementation(project(":myddd-extensions:organisation:organisation-infra"))
    testImplementation(project(":myddd-extensions:organisation:organisation-application"))
}
