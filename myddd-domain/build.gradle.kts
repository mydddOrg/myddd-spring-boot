plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


dependencies {
    api("javax.inject:javax.inject:1")
    api("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    api("org.eclipse.persistence:javax.persistence:2.2.1")

    api(project(":myddd-lang"))
    api(project(":myddd-utils"))
    api(project(":myddd-ioc:myddd-ioc-api"))

    testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit.version"]}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")

    testImplementation(project(":myddd-libs:myddd-distributed-id"))

    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation("com.h2database:h2:${rootProject.extra["h2_version"]}")
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))

}
