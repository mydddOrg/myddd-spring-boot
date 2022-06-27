plugins {
    `java-library`
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!


extra["qcloud.cos.version"] = "5.6.38"


dependencies {
    implementation(project(":myddd-extensions:myddd-media:myddd-media-domain"))
    implementation(project("::myddd-lang"))

    implementation("com.google.code.gson:gson:${rootProject.extra["gson_version"]}")
    implementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")

    implementation("com.qcloud:cos_api:${project.extra["qcloud.cos.version"]}")

    implementation("org.springframework.boot:spring-boot-starter:${rootProject.extra["spring.boot"]}")

    testImplementation(project(":myddd-domain"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}

sonarqube {
    isSkipProject = true
}