plugins {
    `java-library`
    id("org.springframework.boot")
}

group = "org.myddd.extensions.media"
version = rootProject.extra["projectVersion"]!!

val media = if(rootProject.hasProperty("media")) rootProject.property("media") else "local"

dependencies {

    implementation(project(":myddd-extensions:media:media-domain"))
    implementation(project(":myddd-extensions:media:media-infra"))
    implementation(project(":myddd-extensions:media:media-api"))
    implementation(project(":myddd-extensions:media:media-application"))

    implementation("org.springframework.boot:spring-boot-starter-web:${rootProject.extra["spring.boot"]}")

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-libs:myddd-grpc-rest-advice"))

    implementation("org.mariadb.jdbc:mariadb-java-client:${rootProject.extra["mariadb-java-client"]}")
    implementation("com.h2database:h2:${rootProject.extra["h2_version"]}")

    when(media){
        "local" -> implementation(project(":myddd-extensions:media:media-storage:media-storage-local"))
        "aliyun" -> implementation(project(":myddd-extensions:media:media-storage:media-storage-aliyun"))
        "qcloud" -> implementation(project(":myddd-extensions:media:media-storage:media-storage-qcloud"))
        else -> implementation(project(":myddd-extensions:media:media-storage:media-storage-gridfs"))
    }

    implementation("io.grpc:grpc-netty:${rootProject.extra["grpc-version"]}")

    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:${rootProject.extra["jaxb-impl-version"]}")
    implementation("org.glassfish.jaxb:jaxb-runtime:${rootProject.extra["jaxb-impl-version"]}")
    implementation("javax.activation:activation:1.1.1")

    testImplementation("commons-codec:commons-codec:${rootProject.extra["commons-codec"]}")
}

sonarqube {
    isSkipProject = true
}