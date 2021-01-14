plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!


extra["aliyun.sdk.version"] = "3.8.0"

dependencies {
    implementation(project(":myddd-extentions:myddd-media:myddd-media-api"))
    implementation("com.aliyun.oss:aliyun-sdk-oss:${project.extra["aliyun.sdk.version"]}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    implementation("commons-logging:commons-logging:1.2")
    implementation("commons-io:commons-io:2.7")

}