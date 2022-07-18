plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:myddd-media:myddd-media-api"))
    implementation(project(":myddd-extensions:myddd-media:myddd-media-domain"))


    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))

    testImplementation(project(":myddd-extensions:myddd-media:myddd-media-infra"))
    testImplementation(project(":myddd-extensions:myddd-media:myddd-media-storage:myddd-media-storage-local"))
}