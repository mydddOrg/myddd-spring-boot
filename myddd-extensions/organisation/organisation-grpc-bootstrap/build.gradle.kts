plugins {
    `java-library`
    id("org.springframework.boot")
    idea
}

group = "org.myddd.extensions.organisation"
version = rootProject.extra["projectVersion"]!!

val media = if(rootProject.hasProperty("media")) rootProject.property("media") else "gridfs"

dependencies {

    implementation(project(":myddd-extensions:organisation:organisation-domain"))
    implementation(project(":myddd-extensions:organisation:organisation-infra"))
    implementation(project(":myddd-extensions:organisation:organisation-api"))
    implementation(project(":myddd-extensions:organisation:organisation-application"))
    implementation(project(":myddd-extensions:security:security-grpc-bridge"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-libs:myddd-grpc-rest-advice"))

    implementation("org.mariadb.jdbc:mariadb-java-client:${rootProject.extra["mariadb-java-client"]}")

    implementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")


    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:${rootProject.extra["jaxb-impl-version"]}")
    implementation("org.glassfish.jaxb:jaxb-runtime:${rootProject.extra["jaxb-impl-version"]}")
    implementation("javax.activation:activation:1.1.1")

    implementation("org.springframework.boot:spring-boot-starter-log4j2:${rootProject.extra["spring.boot"]}")
}

sonarqube {
    isSkipProject = true
}