plugins {
    `java-library`
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":myddd-extensions:media:media-domain"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))

    implementation(project(":myddd-ioc:myddd-ioc-spring"))

    testImplementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")
    testImplementation(project(":myddd-extensions:media:media-storage:media-storage-local"))
}
