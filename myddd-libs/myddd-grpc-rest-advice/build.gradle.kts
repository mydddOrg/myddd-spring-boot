import com.google.protobuf.gradle.*

plugins {
    `java-library`
    idea
    id("com.google.protobuf")
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true

dependencies {
    implementation(project(":myddd-lang"))
    implementation("io.grpc:grpc-api:${rootProject.extra["grpc-version"]}")
    implementation("com.google.guava:guava:${rootProject.extra["guava.version"]}")

    implementation(project(":myddd-libs:myddd-rest-advice"))
    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    testImplementation("io.grpc:grpc-api:${rootProject.extra["grpc-version"]}")
    testImplementation("io.grpc:grpc-protobuf:${rootProject.extra["grpc-version"]}")
    testImplementation("io.grpc:grpc-stub:${rootProject.extra["grpc-version"]}")
    implementation(project(":myddd-grpc"))

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

sonar {
    isSkipProject = true
}
