plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.cache"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-commons:cache:cache-api"))

    implementation("org.springframework.boot:spring-boot-starter-data-redis:${rootProject.extra["spring.boot"]}")
//    使用lettuce实现
    implementation("io.lettuce:lettuce-core:6.2.0.RELEASE")
    implementation(project(":myddd-ioc:myddd-ioc-api"))

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}

sonar {
    isSkipProject = true
}