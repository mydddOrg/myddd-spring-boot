plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

val oauth2Version = "0.1.0-SNAPSHOT"

dependencies {


    implementation(project(":myddd-commons:verification:verification-api"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")
    implementation("org.springframework.boot:spring-boot-starter-security:${rootProject.extra["spring.boot"]}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${rootProject.extra["spring.boot"]}")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:${rootProject.extra["spring.boot"]}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra["spring.boot"]}")

    implementation("org.myddd.security:spring-security-oauth2:${oauth2Version}")

    implementation(project(":myddd-extensions:security:security-oauth2:security-oauth2-api"))
    implementation(project(":myddd-extensions:security:security-oauth2:security-oauth2-domain"))

    implementation(project(":myddd-domain"))

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    testImplementation(project("::myddd-ioc:myddd-ioc-spring"))

    testImplementation(project(":myddd-commons:verification:verification-application"))
    testImplementation(project(":myddd-commons:verification:verification-infra:verification-gateway-mock"))
    testImplementation(project(":myddd-commons:cache:cache-guava"))
}

sonarqube {
    isSkipProject = true
}