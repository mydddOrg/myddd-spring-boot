plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":myddd-extensions:security:security-domain"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")

    api("org.springframework.security:spring-security-crypto:5.5.1")
}
