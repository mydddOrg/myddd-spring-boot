plugins {
    java
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "2.6.4"
    jacoco
    id("org.sonarqube") version "3.3"
}

val projectVersion = "2.0.2-alpha1"

extra["projectVersion"] = projectVersion
extra["slf4jVersion"] = "1.7.32"
extra["spring.boot"] = "2.6.4"
extra["junit.version"] = "5.8.2"
extra["guava.version"] = "31.0.1-jre"
extra["mockito.version"] = "4.1.0"
extra["dubbo_version"] = "3.0.1"
extra["h2_version"] = "1.4.200"
extra["gson.version"] = "2.8.9"
extra["commons-lang3.version"] = "3.12.0"

extra["javax_inject_version"] = "1"

extra["jakarta_persistence_api_version"] = "2.2.3"
extra["jakarta_transaction_api_version"] = "1.3.3"

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


allprojects {
    tasks.withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}



subprojects {

    apply(plugin = "java")
    apply(plugin = "org.sonarqube")
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

    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }

    tasks.jacocoTestReport {
        dependsOn(tasks.test) // tests are required to run before generating the report
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

        if(!this.sonarqube.isSkipProject){
            tasks.check {
                finalizedBy(tasks.jacocoTestCoverageVerification) // report is always generated after tests run
            }

            tasks.jacocoTestCoverageVerification {
                violationRules {
                    rule {
                        limit {
                            minimum = "0.8".toBigDecimal()
                        }
                    }
                }
            }
        }

    }

    //默认测试依赖
    dependencies {
        testImplementation("org.mockito:mockito-core:${rootProject.extra["mockito.version"]}")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit.version"]}")
        testImplementation("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit.version"]}")
        testImplementation("org.springframework.boot:spring-boot-starter-test:${rootProject.extra["spring.boot"]}")

        testImplementation("com.h2database:h2:${rootProject.extra["h2_version"]}")
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