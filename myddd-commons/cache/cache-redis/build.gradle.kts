plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.cache"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis:${rootProject.extra["spring.boot"]}")
}

sonarqube {
    isSkipProject = true
}