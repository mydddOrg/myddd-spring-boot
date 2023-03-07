plugins {
    kotlin("jvm")
    idea
    id("org.springframework.boot")
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!


dependencies {

    implementation(project(":examples:kotlin-ddd:domain"))
    implementation(project(":examples:kotlin-ddd:infra"))
    implementation(project(":examples:kotlin-ddd:api"))
    implementation(project(":examples:kotlin-ddd:application"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

    implementation("com.sun.xml.bind:jaxb-impl:2.3.6")
    implementation("javax.activation:activation:1.1.1")

    //实际数据库按你选择的替换此JDBC驱动依赖
    implementation("com.h2database:h2:${rootProject.extra["h2_version"]}")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.6")

}

tasks.bootJar {
    enabled = true
}

sonarqube {
    isSkipProject = true
}