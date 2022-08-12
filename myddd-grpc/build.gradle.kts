
import com.google.protobuf.gradle.*

plugins {
    `java-library`
    id("idea")
    id("com.google.protobuf")
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!


dependencies {
    api("io.grpc:grpc-api:${rootProject.extra["grpc-version"]}")
    api("io.grpc:grpc-protobuf:${rootProject.extra["grpc-version"]}")
    api("io.grpc:grpc-stub:${rootProject.extra["grpc-version"]}")
    api(project(":myddd-lang"))
    api("javax.annotation:javax.annotation-api:${rootProject.extra["annotation-api"]}")

    testImplementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")
    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation(project(":myddd-ioc:myddd-ioc-api"))
}

sourceSets.test {
    proto.srcDir("src/test/protobuf")
}


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra["protobuf"]}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${rootProject.extra["grpc-version"]}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

sonarqube {
    isSkipProject = true
}

