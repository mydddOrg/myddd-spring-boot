plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {
    implementation(project(":myddd-extensions:media:media-domain"))
    implementation(project(":myddd-lang"))

    implementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")
    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")

    testImplementation(project(":myddd-domain"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}

sonarqube {
    isSkipProject = true
}