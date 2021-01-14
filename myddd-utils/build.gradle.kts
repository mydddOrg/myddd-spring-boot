plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api("org.apache.commons:commons-lang3:3.10")
    api("com.google.code.gson:gson:2.8.6")
    api("org.slf4j:slf4j-api:${rootProject.extra["slf4jVersion"]}")
    testImplementation("org.slf4j:slf4j-log4j12:${rootProject.extra["slf4jVersion"]}")
}