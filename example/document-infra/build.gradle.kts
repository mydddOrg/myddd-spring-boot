plugins {
    `java-library`
}

group = "cc.lingenliu.document"
version = rootProject.extra["projectVersion"]!!

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {

    implementation(project(":example:document-domain"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
    testImplementation("com.h2database:h2:1.4.200")
}
