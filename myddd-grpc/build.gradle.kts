plugins {
    `java-library`
    id("idea")
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!


dependencies {
    api("io.grpc:grpc-api:${rootProject.extra["grpc-version"]}")
    api("io.grpc:grpc-protobuf:${rootProject.extra["grpc-version"]}")
    api("io.grpc:grpc-stub:${rootProject.extra["grpc-version"]}")
    api(project(":myddd-utils"))
    api("javax.annotation:javax.annotation-api:${rootProject.extra["annotation-api"]}")
}

sonarqube {
    isSkipProject = true
}