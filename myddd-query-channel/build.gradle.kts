plugins {
    java
    `java-library`
}


group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    api(project(":myddd-domain"))

    api("javax.transaction:javax.transaction-api:${rootProject.extra["javax_transaction_api_version"]}")
    api("javax.persistence:javax.persistence-api:${rootProject.extra["javax_persistence_api_version"]}")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra["spring.boot"]}")


    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}