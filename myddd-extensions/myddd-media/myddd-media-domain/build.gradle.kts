plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api(project(":myddd-domain"))
    api("javax.inject:javax.inject:${rootProject.extra["javax_inject_version"]}")
    api("jakarta.transaction:jakarta.transaction-api:${rootProject.extra["jakarta_transaction_api_version"]}")

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")

    testImplementation(project(":myddd-extensions:myddd-media:myddd-media-infra"))
    testImplementation(project(":myddd-extensions:myddd-media:myddd-media-storage:myddd-media-storage-local"))

}