plugins {
    `java-library`
}

group = "org.myddd.java"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":examples:java-ddd:domain"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))

}

sonarqube {
    isSkipProject = true
}