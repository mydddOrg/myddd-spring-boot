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

//myddd对GRPC的支持
include(":myddd-grpc")

//支持第三方能力的类库
include(":myddd-libs:myddd-distributed-id")
include(":myddd-libs:myddd-rest-advice")
include(":myddd-libs:myddd-grpc-rest-advice")


//commons 验证码模块（支持手机验证码与邮箱验证码）
include(":myddd-commons:verification:verification-api")
include(":myddd-commons:verification:verification-application")
include(":myddd-commons:verification:verification-infra:verification-gateway-mock")
include(":myddd-commons:verification:verification-infra:verification-gateway-email")
include(":myddd-commons:verification:verification-rest")

//cache缓存通用模块
include(":myddd-commons:cache:cache-api")
include(":myddd-commons:cache:cache-guava")
include(":myddd-commons:cache:cache-redis")

//通用健康检查
include(":myddd-commons:healthy")

//uid分布式主键生成策略
include(":myddd-commons:uid:uid-generator")
include(":myddd-commons:uid:uid-worker-id-assigner")
include(":myddd-commons:uid:uid-generator-adapter")

//示例项目
include(":examples:kotlin-ddd:api")
include(":examples:kotlin-ddd:application")
include(":examples:kotlin-ddd:domain")
include(":examples:kotlin-ddd:infra")
include(":examples:kotlin-ddd:bootstrap")

include(":examples:java-ddd:api")
include(":examples:java-ddd:application")
include(":examples:java-ddd:domain")
include(":examples:java-ddd:infra")
include(":examples:java-ddd:bootstrap")
