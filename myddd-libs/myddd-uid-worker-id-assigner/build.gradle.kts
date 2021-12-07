plugins {
    `java-library`
    id("idea")
}

group = "org.myddd"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-domain"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))

    testImplementation(project(":myddd-ioc:myddd-ioc-spring"))
    testImplementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
}
