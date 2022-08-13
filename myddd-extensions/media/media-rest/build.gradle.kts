plugins {
    `java-library`
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":myddd-extensions:media:media-api"))
    implementation(project(":myddd-libs:myddd-rest-advice"))

    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-lang"))
    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))

    testImplementation(project(":myddd-extensions:media:media-storage:media-storage-local"))
    testImplementation(project(":myddd-extensions:media:media-infra"))
    testImplementation(project(":myddd-extensions:media:media-application"))
}
