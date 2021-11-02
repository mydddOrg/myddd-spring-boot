plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    api(project(":myddd-domain"))
    implementation(project(":myddd-query-channel"))

    api("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${rootProject.extra["spring.boot"]}")
    implementation("org.eclipse.persistence:javax.persistence:2.2.1")
    implementation("javax.inject:javax.inject:1")


    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))

}