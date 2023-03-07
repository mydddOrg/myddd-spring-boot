plugins {
    `java-library`
    id("org.springframework.boot")
}

group = "org.myddd.java"
version = rootProject.extra["projectVersion"]!!


dependencies {

    implementation(project(":examples:java-ddd:domain"))
    implementation(project(":examples:java-ddd:infra"))
    implementation(project(":examples:java-ddd:api"))
    implementation(project(":examples:java-ddd:application"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-libs:myddd-distributed-id"))

    //实际数据库按你选择的替换此JDBC驱动依赖
    implementation("com.h2database:h2:${rootProject.extra["h2_version"]}")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.6")


    implementation("com.google.code.gson:gson:2.9.0")

    //修复JDK11 JAXBExceptio的错误
    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0")
    implementation("javax.activation:activation:1.1.1")



}

tasks.bootJar {
    enabled = true
}

sonarqube {
    isSkipProject = true
}