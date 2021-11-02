plugins {
    `java-library`
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    api("com.google.guava:guava:${rootProject.extra["guava.version"]}")


    api("org.apache.commons:commons-lang3:3.12.0")
    api("com.google.code.gson:gson:2.8.8")
    api("org.slf4j:slf4j-api:${rootProject.extra["slf4jVersion"]}")
    implementation("com.caucho:hessian:4.0.65")
    testImplementation("org.slf4j:slf4j-log4j12:${rootProject.extra["slf4jVersion"]}")
}