rootProject.name = "myddd-java"

//基础依赖层
include(":myddd-lang")

//一些基础类库
include(":myddd-utils")

//领域层
include(":myddd-domain")

//ioc依赖注入的spring实现
include(":myddd-ioc:myddd-ioc-api")
include(":myddd-ioc:myddd-ioc-spring")

//仓储的JPA实现
include(":myddd-persistence:myddd-persistence-jpa")

//一个查询通道的实现
include(":myddd-query-channel")

include(":myddd-libs:myddd-distributed-id")
include(":myddd-libs:myddd-rest-advice")
include(":myddd-libs:myddd-rest-advice-dubbo")
include(":myddd-libs:myddd-dubbo-filter")


//media extension
include(":myddd-extensions:myddd-media:myddd-media-domain")
include(":myddd-extensions:myddd-media:myddd-media-infra")
include(":myddd-extensions:myddd-media:myddd-media-storage:myddd-media-storage-qcloud")
include(":myddd-extensions:myddd-media:myddd-media-storage:myddd-media-storage-aliyun")
include(":myddd-extensions:myddd-media:myddd-media-storage:myddd-media-storage-local")
include(":myddd-extensions:myddd-media:myddd-media-storage:myddd-media-storage-gridfs")


include(":myddd-extensions:myddd-security:myddd-security-domain")
include(":myddd-extensions:myddd-security:myddd-security-infra")
