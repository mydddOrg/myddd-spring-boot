plugins {
    java
    `java-library`
}


group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    api(project(":myddd-domain"))


    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra["spring.boot"]}")
    api("org.eclipse.persistence:javax.persistence:2.2.1")
    api("javax.inject:javax.inject:1")

    testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit.version"]}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")

    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation("com.h2database:h2:${rootProject.extra["h2_version"]}")
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))

}