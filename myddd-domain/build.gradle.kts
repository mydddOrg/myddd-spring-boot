plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


dependencies {
    api("javax.inject:javax.inject:1")
    api("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    api("org.eclipse.persistence:javax.persistence:2.2.1")

    api(project(":myddd-lang"))
    api(project(":myddd-utils"))

    testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit.version"]}")
}
