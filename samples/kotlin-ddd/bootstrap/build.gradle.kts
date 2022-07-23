plugins {
    kotlin("jvm")
    idea
    id("org.springframework.boot")
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!


dependencies {

    implementation(project(":samples:kotlin-ddd:domain"))
    implementation(project(":samples:kotlin-ddd:infra"))
    implementation(project(":samples:kotlin-ddd:api"))
    implementation(project(":samples:kotlin-ddd:application"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-ioc:myddd-ioc-api"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-utils"))

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3")

    implementation("javax.xml.bind:jaxb-api:2.3.1")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.1")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0")
    implementation("javax.activation:activation:1.1.1")

    //实际数据库按你选择的替换此JDBC驱动依赖
    implementation("com.h2database:h2:${rootProject.extra["h2_version"]}")

    testImplementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
}

tasks.bootJar {
    enabled = true
}

sonarqube {
    isSkipProject = true
}