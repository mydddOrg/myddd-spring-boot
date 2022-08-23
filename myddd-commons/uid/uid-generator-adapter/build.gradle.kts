plugins {
    `java-library`
    id("idea")
}

group = "org.myddd.commons"
version = rootProject.extra["projectVersion"]!!

extra["disableTestDistributedId"] = "true"

dependencies {
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-commons:uid:uid-worker-id-assigner"))
    implementation(project(":myddd-commons:uid:uid-generator"))
    implementation(project(":myddd-ioc:myddd-ioc-api"))
    implementation("org.springframework.boot:spring-boot-starter:${rootProject.extra["spring.boot"]}")
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}
