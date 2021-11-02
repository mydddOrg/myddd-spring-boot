plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    api("javax.inject:javax.inject:1")
    api("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    api("org.eclipse.persistence:javax.persistence:2.2.1")

    api(project(":myddd-lang"))
    api(project(":myddd-utils"))
    api(project(":myddd-ioc:myddd-ioc-api"))


    testImplementation(project(":myddd-libs:myddd-distributed-id"))
    testImplementation(project(":myddd-persistence:myddd-persistence-jpa"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}
