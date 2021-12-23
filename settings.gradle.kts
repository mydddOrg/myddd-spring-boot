rootProject.name = "myddd-java"

//基础依赖层
include("myddd-lang")

//一些基础类库
include("myddd-utils")

//领域层
include("myddd-domain")

//ioc依赖注入的spring实现
include("myddd-ioc:myddd-ioc-api")
include("myddd-ioc:myddd-ioc-spring")

//仓储的JPA实现
include("myddd-persistence:myddd-persistence-jpa")

//一个查询通道的实现
include("myddd-query-channel")


//plugin
include(":plugin:dubbo-protobuf-gradle-plugin")

include(":myddd-libs:myddd-distributed-id")
include(":myddd-libs:myddd-rest-advice")
include(":myddd-libs:myddd-rest-advice-dubbo")
include(":myddd-libs:myddd-dubbo-filter")