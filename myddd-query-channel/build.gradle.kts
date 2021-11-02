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


    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}