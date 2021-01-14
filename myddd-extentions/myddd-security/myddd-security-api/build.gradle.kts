plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-utils"))
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.11.0")

}