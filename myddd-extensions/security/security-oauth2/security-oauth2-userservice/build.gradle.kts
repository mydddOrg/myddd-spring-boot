plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:security:security-oauth2:security-oauth2-core"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")
    implementation("org.springframework.boot:spring-boot-starter-security:${rootProject.extra["spring.boot"]}")
    implementation(project(":myddd-ioc:myddd-ioc-api"))

    implementation(project(":myddd-extensions:security:security-api"))

    testImplementation(project(":myddd-extensions:security:security-application"))
    testImplementation(project(":myddd-extensions:security:security-infra"))

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}

sonarqube {
    isSkipProject = true
}