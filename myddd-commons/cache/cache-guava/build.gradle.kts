plugins {
    `java-library`
    idea
}

group = "org.myddd.commons.cache"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    api("javax.inject:javax.inject:${rootProject.extra["javax_inject_version"]}")
    implementation(project(":myddd-commons:cache:cache-api"))
    implementation("com.google.guava:guava:${rootProject.extra["guava.version"]}")
    implementation(project(":myddd-ioc:myddd-ioc-api"))

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
}