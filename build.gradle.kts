plugins {
    java
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "2.5.5"
    jacoco
}

val projectVersion = "1.3.0-SNAPSHOT"

extra["projectVersion"] = projectVersion
extra["slf4jVersion"] = "1.7.32"
extra["spring.boot"] = "2.5.5"
extra["junit.version"] = "5.8.1"
extra["guava.version"] = "31.0.1-jre"
extra["mockito.version"] = "4.0.0"
extra["dubbo_version"] = "3.0.1"
extra["h2_version"] = "1.4.200"
extra["gson.version"] = "2.8.8"
extra["commons-lang3.version"] = "3.12.0"

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
    apply(plugin = "jacoco")

    jacoco {
        toolVersion = "0.8.7"
    }

    tasks.jacocoTestReport {
        reports {
            xml.required.set(true)
            csv.required.set(false)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

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

                            val releasesRepoUrl = "sftp://ssh.myddd.org:10010/repositories/releases"
                            val snapshotsRepoUrl = "sftp://ssh.myddd.org:10010/repositories/snapshots"
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

        val enableJunit = this.extra.has("enableJunit")
        if(enableJunit){

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


