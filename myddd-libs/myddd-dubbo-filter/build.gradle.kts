plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true
extra["dubbo_serialization_version"] = "2.7.13"
extra["dubbo_version"] = "3.0.1"

dependencies {
    implementation(project(":myddd-lang"))
    implementation("org.apache.dubbo:dubbo-rpc-api:${project.extra["dubbo_version"]}")
}

sonarqube {
    isSkipProject = true
}