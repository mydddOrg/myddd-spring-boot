import com.google.protobuf.gradle.*

plugins {
    `java-library`
    id("com.google.protobuf")
    idea
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api(project(":myddd-grpc"))
}

sourceSets.main {
    proto.srcDir("src/main/protobuf")
    java.srcDir("build/generated/proto/main/myddd-grpc")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra["protobuf"]}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.extra["grpc-version"]}"
        }
        id("myddd-grpc") {
            artifact = "org.myddd.plugin:myddd-grpc-gradle-plugin:${rootProject.extra["myddd-grpc-plugin"]}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("myddd-grpc")
            }
        }
    }
}

sonarqube {
    isSkipProject = true
}