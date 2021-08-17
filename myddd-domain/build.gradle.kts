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
    api(project(":myddd-utils"))
    api("javax.inject:javax.inject:1")
    api("org.eclipse.persistence:javax.persistence:2.2.1")

    api(project(":myddd-lang"))

    testImplementation(project(":myddd-utils"))
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit.version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit.version"]}")
}
