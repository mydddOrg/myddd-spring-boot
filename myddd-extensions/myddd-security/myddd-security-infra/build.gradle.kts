plugins {
    `java-library`
    id("idea")
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":myddd-extensions:myddd-security:myddd-security-domain"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")

    api("org.springframework.security:spring-security-crypto:5.5.1")
}
