import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "2.5.3"
}

val projectVersion = "1.3.0-SNAPSHOT"

extra["projectVersion"] = projectVersion
extra["slf4jVersion"] = "1.7.30"
extra["spring.boot"] = "2.5.3"
extra["junit.version"] = "5.7.2"

allprojects {
    repositories {

        maven {
            setUrl("https://maven.aliyun.com/repository/public/")
        }
        maven {
            setUrl("https://maven.aliyun.com/repository/spring/")
        }

        mavenCentral()
    }
}

repositories {

    maven {
        setUrl("https://maven.aliyun.com/repository/public/")
    }
    maven {
        setUrl("https://maven.aliyun.com/repository/spring/")
    }

    mavenCentral()
}




subprojects {

    apply(plugin = "java")

    afterEvaluate {
        val publishJar = this.extra.has("publishJar")
        if(publishJar){
            apply(plugin = "maven-publish")

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
                                username = if(project.hasProperty("username")) project.property("username") as String? else ""
                                password = if(project.hasProperty("password")) project.property("password") as String? else ""
                            }

                        }
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


