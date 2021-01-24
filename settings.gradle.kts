rootProject.name = "myddd-backend"

//一些基础类库
include("myddd-utils")

//领域层
include("myddd-domain")

//ioc依赖注入的spring实现
include("myddd-ioc:myddd-ioc-spring")

//仓储的JPA实现
include("myddd-persistence:myddd-persistence-jpa")

//一个查询通道的实现
include("myddd-query-channel")

//扩展功能模块 -- 媒体模块API用于上传下载
include("myddd-extentions:myddd-media:myddd-media-api")
//扩展功能模块 -- 媒体模块实现,基于阿里云OSS
include("myddd-extentions:myddd-media:myddd-media-aliyun")

//扩展功能模块 -- 基于Spring Security的一个OAuth2实现
include("myddd-extentions:myddd-security:myddd-security-api")
include("myddd-extentions:myddd-security:myddd-security-login")
include("myddd-extentions:myddd-security:myddd-security-oauth2")


//document示例项目
include(":example:document-domain")
include(":example:document-infra")
include(":example:document-api")
include(":example:document-application")
include(":example:document-starter")



