plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true
extra["enableJunit"] = true


dependencies {
    api("org.slf4j:slf4j-api:${rootProject.extra["slf4jVersion"]}")
    testImplementation("org.slf4j:slf4j-log4j12:${rootProject.extra["slf4jVersion"]}")
}
