plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:media:media-api"))
    implementation(project(":myddd-extensions:media:media-domain"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))

    testImplementation(project(":myddd-extensions:media:media-infra"))
    testImplementation(project(":myddd-extensions:media:media-storage:media-storage-local"))

}