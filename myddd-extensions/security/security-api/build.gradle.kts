import com.google.protobuf.gradle.*

plugins {
    `java-library`
    id("com.google.protobuf")
    idea
}

group = "org.myddd.extensions.security"
version = rootProject.extra["projectVersion"]!!

dependencies {
    api("com.google.protobuf:protobuf-java:${rootProject.extra["protoc_version"]}")
    api("javax.annotation:javax.annotation-api:1.3.2")
    implementation(project(":myddd-utils"))
}

sourceSets.main {
    proto.srcDir("src/main/protobuf")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra["protobuf-java"]}"
    }
    plugins {
        id("myddd-dubbo") {
            artifact = "org.myddd.plugin:dubbo-protobuf-gradle-plugin:${rootProject.extra["dubbo-protobuf-gradle-plugin"]}"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("myddd-dubbo")
            }
        }
    }
}

sonarqube {
    isSkipProject = true
}