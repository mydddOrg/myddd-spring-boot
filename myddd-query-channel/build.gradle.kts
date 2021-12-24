plugins {
    java
    `java-library`
}


group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    api(project(":myddd-domain"))

    api("jakarta.persistence:jakarta.persistence-api:${rootProject.extra["jakarta_persistence_api_version"]}")
    api("jakarta.transaction:jakarta.transaction-api:${rootProject.extra["jakarta_transaction_api_version"]}")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra["spring.boot"]}")

    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}