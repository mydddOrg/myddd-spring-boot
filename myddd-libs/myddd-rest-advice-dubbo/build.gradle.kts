plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true
extra["dubbo_serialization_version"] = "2.7.13"

dependencies {
    implementation(project(":myddd-lang"))

    implementation(project(":myddd-libs:myddd-rest-advice"))
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")
    implementation("org.apache.dubbo:dubbo-serialization-protobuf:${project.extra["dubbo_serialization_version"]}")
    testImplementation(project(":myddd-domain"))
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}

sonarqube {
    isSkipProject = true
}
