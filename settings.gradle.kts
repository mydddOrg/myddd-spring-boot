rootProject.name = "myddd-spring-boot"

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

//支持第三方能力的类库
include(":myddd-libs:myddd-distributed-id")
include(":myddd-libs:myddd-rest-advice")
include(":myddd-libs:myddd-dubbo-filter")

//media extension
include(":myddd-extensions:media:media-domain")
include(":myddd-extensions:media:media-infra")
include(":myddd-extensions:media:media-storage:media-storage-qcloud")
include(":myddd-extensions:media:media-storage:media-storage-aliyun")
include(":myddd-extensions:media:media-storage:media-storage-local")
include(":myddd-extensions:media:media-storage:media-storage-gridfs")
include(":myddd-extensions:media:media-api")
include(":myddd-extensions:media:media-application")

//用户权限模块
include(":myddd-extensions:security:security-domain")
include(":myddd-extensions:security:security-infra")
include(":myddd-extensions:security:security-api")
include(":myddd-extensions:security:security-application")

//组织模块
include(":myddd-extensions:organisation:organisation-domain")
include(":myddd-extensions:organisation:organisation-infra")

//commons通用模块
include(":myddd-commons:verification:verification-api")
include(":myddd-commons:verification:verification-application")
include(":myddd-commons:verification:verification-infra:verification-gateway-mock")
include(":myddd-commons:verification:verification-infra:verification-gateway-email")
include(":myddd-commons:verification:verification-rest")
