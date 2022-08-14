# myddd-spring-boot

myddd-spring-boot是基于整洁架构及领域驱动理念而构建的类库，用于支持基于spring boot的后端开发，支持Java以及Kotlin两种语言。

***总览图***
![](https://images.taoofcoding.tech/2022/08/myddd-spring-boot-2022-08-14.png)

## 快速开始

myddd starter已经开放使用，现在开始，你可以访问[myddd starter](https://starter.myddd.org)以快速开始生成一个myddd项目。

在myddd starter中，你可以选择Java或Kotlin两种语言中的任意一个来生成项目。

## 示例项目

如果你想直接查看示例项目，可以访问如下：

* Java语言示例项目： 位于examples目录下的`java-ddd`
* Kotlin示例项目：位于examples目录下的`kotlin-ddd`
* `Java + gRPC + 容器编排`的云原生架构，参照[`java-grpc-sample`](https://github.com/mydddOrg/java-grpc-sample)项目

## myddd-spring-boot的原则与宗旨

myddd-spring-boot始终坚持以下两个原则：

1. 最大化的倡导ddd编码风格与模式

2. 把支持Spring Boot做为第一要务。

   > Spring Boot结合Spring Cloud框架，已成为中小企业，个人开发者开发的不二选择。选择支持Spring Boot，也是期望为更多的人与团队服务。在不影响他们的整体架构的选型下，更好的使用DDD

## SonarQube质量报告

当前主干的SonarQube的质量状态为:

**版本：0.3.3-ALPHA**

![SonarQube质量报告](https://images.taoofcoding.tech/sonar/sonar-data-of-myddd-0.3.3-ALPHA.png)


## 为什么叫myddd

ddd领域驱动的理念较为复杂，概念较多。包含实体，值对象，仓储，领域服务，领域事件，聚合根，应用服务，查询通道，DTO数据对象等众多要素。

因此大家对于它的理解与争议较多，我个人不是非常喜欢与人争论，将自己对DDD的理解，结合自己十多年在后台，移动端（iOS,Android)，基于Electron的桌面开发以及前端（TypeScript + React）的技术经验的基础上，取之名为myddd，表意为：ddd，我理解，我实现。不与人陷入争议之中。


## 官网

【myddd官网】: https://myddd.org

【微言码道】官网：https://taoofcoding.tech

## 致敬

向[dddlib](https://github.com/dayatang/dddlib) 致敬。**myddd-java最初**来源于[dddlib](https://github.com/dayatang/dddlib).

数年前，我有幸与dddlib开发者杨宇老师一同工作，使用dddlib，讨论DDD，参与dddlib的开发。一晃数年已过。
我与杨宇老师仍保持密切交流。

**myddd-spring-boot**来源于dddlib，无论何时，我都会明白无误的说明这一点。 如今杨宇老师已无精力关注dddlib，便决定基于它重整DDD。

> 我已经按照自己对ddd的理解，对myddd-spring-boot进行了一次较大规模的重构
> 并且自重构后，已经按照TDD的方式来开发与维护myddd-spring-boot,并且加入了SonarQube质量管控，单元测试覆盖率不少于80%。
