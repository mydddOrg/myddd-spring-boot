import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    `java-library`
    id("org.springframework.boot")
}

apply(plugin = "io.spring.dependency-management")

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}


dependencies {

    implementation("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.platform:junit-platform-launcher:1.5.2")

    implementation("javax.inject:javax.inject:1")

    implementation(project(":myddd-query-channel"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-extensions:myddd-security:myddd-security-api"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))



    implementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
}