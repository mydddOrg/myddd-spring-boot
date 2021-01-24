plugins {
    `java-library`
}

group = "cc.lingenliu.document"
version = rootProject.extra["projectVersion"]!!


dependencies {
    implementation(project(":myddd-utils"))
    implementation("org.jboss.spec.javax.transaction:jboss-transaction-api_1.2_spec:1.1.1.Final")

}
