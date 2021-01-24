plugins {
    `java-library`
}

group = "cc.lingenliu.document"
version = rootProject.extra["projectVersion"]!!

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
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

    testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")
    testImplementation("com.h2database:h2:1.4.200")
    testImplementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")


}
