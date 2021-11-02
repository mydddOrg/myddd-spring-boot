plugins {
    `java-library`
}

group = "org.myddd"
version = "0.0.6"

extra["publishJar"] = true

dependencies {
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("com.salesforce.servicelibs:jprotoc:1.2.0")

}

sonarqube {
    isSkipProject = true
}
