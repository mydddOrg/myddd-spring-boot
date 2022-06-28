plugins {
    `java-library`
    `idea`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    api("javax.inject:javax.inject:${rootProject.extra["javax_inject_version"]}")
    api("jakarta.persistence:jakarta.persistence-api:${rootProject.extra["jakarta_persistence_api_version"]}")

    api("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    api(project(":myddd-lang"))
    api(project(":myddd-utils"))
    api(project(":myddd-ioc:myddd-ioc-api"))


    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}
