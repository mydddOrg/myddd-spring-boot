plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true
extra["dubbo_version"] = "3.0.9"

dependencies {
    implementation(project(":myddd-lang"))
    implementation("org.apache.dubbo:dubbo-rpc-api:${project.extra["dubbo_version"]}")
}

sonarqube {
    isSkipProject = true
}