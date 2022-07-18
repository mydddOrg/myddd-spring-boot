plugins {
    `java-library`
    idea
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    implementation(project(":myddd-ioc:myddd-ioc-api"))
    implementation("org.springframework.boot:spring-boot:${rootProject.extra["spring.boot"]}")
}
