import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    `java-library`
    id("org.springframework.boot")
}

group = "cc.lingenliu.document"
version = rootProject.extra["projectVersion"]!!

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<BootJar>("bootJar") {
    classifier = "boot"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))


    implementation(project(":example:document-domain"))
    implementation(project(":example:document-infra"))
    implementation(project(":example:document-api"))
    implementation(project(":example:document-application"))

    implementation("mysql:mysql-connector-java:8.0.23")

    implementation("jakarta.xml.bind:jakarta.xml.bind-api:2.3.2")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.2")


    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation("com.h2database:h2:1.4.200")
    testImplementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")


}
