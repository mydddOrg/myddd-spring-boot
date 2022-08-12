plugins {
    `java-library`
}

group = "org.myddd.commons"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation(project(":myddd-ioc:myddd-ioc-api"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}
