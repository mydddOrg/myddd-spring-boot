plugins {
    kotlin("jvm")
    idea
}

group = "org.myddd.kotlin"
version = rootProject.extra["projectVersion"]!!

dependencies {

    implementation(project(":examples:kotlin-ddd:api"))
    implementation(project(":examples:kotlin-ddd:domain"))

    implementation(project(":myddd-utils"))
    implementation(project(":myddd-query-channel"))
    implementation(project(":myddd-ioc:myddd-ioc-spring"))
    implementation(project(":myddd-persistence:myddd-persistence-jpa"))
    implementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")

    testImplementation(project(":examples:kotlin-ddd:infra"))

}

sonarqube {
    isSkipProject = true
}