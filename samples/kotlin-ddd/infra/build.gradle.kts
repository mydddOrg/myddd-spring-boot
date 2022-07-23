plugins {
    kotlin("jvm")
    idea
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":samples:kotlin-ddd:domain"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))

    testImplementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")
}


sonarqube {
    isSkipProject = true
}