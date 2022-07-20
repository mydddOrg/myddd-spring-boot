plugins {
    `java-library`
    idea
}

group = "org.myddd.extensions.organisation"
version = rootProject.extra["projectVersion"]!!

extra["commons-lang3-version"] = "3.12.0"


dependencies {
    implementation(project(":myddd-extensions:organisation:organisation-domain"))
    api(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation("org.apache.commons:commons-lang3:${project.extra["commons-lang3-version"]}")

    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation(project(":myddd-extensions:security:security-api"))
}
