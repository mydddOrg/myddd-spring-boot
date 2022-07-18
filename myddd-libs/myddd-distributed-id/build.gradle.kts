plugins {
    `java-library`
    idea
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!

extra["publishJar"] = true


dependencies {
    implementation(project(":myddd-domain"))

    testImplementation(project(":myddd-utils"))
}

sonarqube {
    isSkipProject = true
}