plugins {
    `java-library`
    idea
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    implementation(project(":myddd-ioc:myddd-ioc-api"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))

    implementation("com.h2database:h2:${rootProject.extra["h2_version"]}")

    implementation("org.mockito:mockito-core:${rootProject.extra["mockito.version"]}")
    implementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit.version"]}")
    implementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit.version"]}")
    implementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
}
