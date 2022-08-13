plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")
    implementation(project(":myddd-extensions:security:security-api"))

    implementation(project(":myddd-commons:verification:verification-api"))
    implementation(project(":myddd-extensions:security:security-oauth2:security-oauth2-api"))


    implementation(project(":myddd-lang"))
    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-libs:myddd-rest-advice"))

    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation(project(":myddd-extensions:security:security-infra"))
    testImplementation(project(":myddd-extensions:security:security-application"))
    testImplementation("org.springframework.security:spring-security-crypto:5.5.1")
    testImplementation(project(":myddd-commons:verification:verification-application"))
    testImplementation(project(":myddd-commons:verification:verification-infra:verification-gateway-mock"))
    testImplementation(project(":myddd-commons:cache:cache-guava"))
}
