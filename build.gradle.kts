import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "2.4.1"
}

val projectVersion = "1.1.0-SNAPSHOT"

extra["projectVersion"] = projectVersion
extra["slf4jVersion"] = "1.7.30"
extra["spring.boot"] = "2.4.1"

allprojects {
    repositories {
        mavenCentral()
    }
}


subprojects {

    apply(plugin = "maven-publish")
    apply(plugin = "java")

    extra["projectVersion"] = "1.1.0-SNAPSHOT"

    publishing {

        publications {

            create<MavenPublication>("mavenJava"){
                groupId = "org.myddd"
                afterEvaluate {
                    artifactId = tasks.jar.get().archiveBaseName.get()
                }
                from(components["java"])
            }

            repositories {
                maven {

                    val releasesRepoUrl = "sftp://ssh.myddd.org:10010/repository/releases"
                    val snapshotsRepoUrl = "sftp://ssh.myddd.org:10010/repository/snapshots"
                    version = projectVersion
                    url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

                    credentials {
                        username = System.getProperty("user")
                        password = System.getProperty("password")
                    }

                }
            }
        }

    }
}


tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

group = "org.myddd"
version = extra["projectVersion"]!!


