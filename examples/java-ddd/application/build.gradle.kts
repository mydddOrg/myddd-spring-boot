plugins {
    `java-library`
}

group = "org.myddd.java"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":examples:java-ddd:api"))
    implementation(project(":examples:java-ddd:domain"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))


    testImplementation(project(":examples:java-ddd:infra"))

}
sonar {
    isSkipProject = true
}