# 基于kotlin与myddd的领域驱动示例

这个项目是一个简单的示例，用于演示基于myddd与Kotlin结合的一个领域驱动示例程序。

用的是myddd-java，也就是Spring Boot基础之上构建的。

## 为什么

myddd现在其实提供了两种领域驱动的实现，分别是：

* 基于Spring Boot与Java的领域驱动实现 (myddd-java)
* 基于Vert.x与Kotlin的响应式领域驱动实现（myddd-vertx)

现在，我添加了基于Spring Boot与Kotlin的领域驱动的实现。

先解释下这样做的原因。

> Spring Boot仍然是企业级开发最主流的选择

其实对大多数公司，特别是国内的公司来说，使用Spring Boot以及Java来做企业级后端开发是最优选择。它非常成熟，可靠，久经考验。并且团队人员的招聘与培训也非常适合。

所以，对于大多数公司或架构师来说，Spring Boot仍然是最好的选择。

> Kotlin是更简洁的`Better Java`

对于企业级开发，特别是使用Spring Boot的公司来说，选择Java语言可能是没有任何疑问的。

但Kotlin也是非常值得考虑的选择。相比Java，它有着独特的优势：

* Kotlin是后来者，它的设计与实现完全是站在Java的肩膀上，所以它比Java更简洁优雅。而Java仍然有非常重的历史负担，阻碍它的大幅度更新。
* Kotlin在函数式编码上远优于Java
* Kotlin从学习曲线上来说，非常低。一个Java程序员并不需要花费太多成本就能快速的上手与使用。
* Kotlin几乎可以无缝的对接Java，使用Java强大的生态

> Spring Boot对Kotlin提供原生支持

自Spring 5之后，Spring就提供了Kotlin的原生支持，将Kotlin视为第一语言进行支持。这意味着使用Spring Boot，你完全可以把Kotlin纳为考虑。

## 优势

所以，基于Spring Boot与Kotlin搭配，是个非常好的组合。Kotlin的优势带来的结果就是：

* 代码更少，更简洁，更优雅
* Kotlin提供了很多函数式的语法，你不会想错过它
* 更易于维护，质量更可靠

## 实现思路

我并没有重新写一个`myddd-kotlin`的类库，虽然我确实曾经有过这样的想法。

但在意识到Kotlin与Java几乎100%兼容后，我发现使用`myddd-java`基础之上，就能编写出Spring Boot + Kotlin的领域驱动模式的代码了。

所谓，实践才能出真知，在意识到这一点后，我就尝试了基于myddd-java来实现一个Spring Boot + Kotlin的组合。

经过一些时间的尝试之后，我认为这是个非常好的组合。

## 示例

我公开了这示例，放在Github上。

地址是： https://github.com/mydddOrg/kotlin-ddd-sample，你可以随时访问这个Git以了解更多。

这是一个功能虽然简单，但比较完整的示例，从领域层，仓储层实现，应用层及Rest Api层都完整的实现，最终能构建的一个Jar进行运行。

并且这个示例每一层都有完整的单元测试。

我在这里仅贴一些代码来展示它的简洁与优雅性。

> 领域层

```kotlin
package org.myddd.kotlin.domain

import org.myddd.domain.BaseIDEntity
import org.myddd.domain.InstanceFactory
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_")
class User:BaseIDEntity() {

    @Column(name = "user_id")
    lateinit var userId:String

    @javax.persistence.Transient
    lateinit var password:String

    @Column(name = "encode_password")
    lateinit var encodePassword:String

    lateinit var name:String

    companion object {
        private val userRepository by lazy { InstanceFactory.getInstance(UserRepository::class.java) }
    }

    fun createUser():User? {
        return userRepository.createUser(this)
    }
}
```

可以看到，相比Java令人厌烦的`getter`以及`setter`方法，kotlin的实现非常简洁。

> 仓储实现层

```kotlin
@Named
class UserRepositoryJPA:AbstractRepositoryJPA(),UserRepository {

    override fun createUser(user: User): User? {
        return save(user)
    }

    override fun searchUserByName(name: String): List<User> {
        return entityManager
            .createQuery("from User where name like :name",User::class.java)
            .setParameter("name","%$name%")
            .resultList
    }
}
```

仓储使用的是JPA。

> DTO

```kotlin
data class UserDTO
    @ConstructorProperties(value = ["id","userId","name","phone","email","password"])
    constructor(val id:Long? = null,val userId:String? = "",val name:String? = "",val phone:String? = "",val email:String? = "",val password:String? = "")
```

使用Kotlin的data类来做DTO。简单方便。

* 请注意，JDK 17也支持data class了

> 利用扩展来实现assembler组装器

```kotlin
fun User.toDTO():UserDTO {
    return UserDTO(id = id, userId = userId, name = name, phone = phone, email = email)
}

fun UserDTO.toUser():User {
    requireNotNull(userId)
    requireNotNull(name)

    val user = User()
    user.id = id
    user.userId = userId!!
    user.name = name!!
    user.phone = phone
    user.email = email
    return user
}
```

Kotlin中有扩展的语法功能，这种实现相比通过继承或组合来扩展已有类的功能，更简洁优雅。

我最开始是在Objective C中接触到这种概念，现在在Kotlin中也能用到，非常高兴。

## 值得考虑的选择

对于大多数公司或架构师来说，使用Vert.x这样的小众异步式的框架，可能是很难的。但基于Spring Boot与Kotlin这样的搭配，我认为是非常值得考虑的。

对于任何一个追求简洁，优雅代码的程序员或团队来说，在不改变Spring Boot这个大前提下，选择Kotlin可以收获到立竿见影的好处。

简洁与优雅，通过与可维护性是有正向关联的。

而可维护性，我认为是当下我们行业最值得重视也是最容易出问题的一个点。

而`myddd-java`也会同时支持java与kotlin，未来可能会加大对kotlin的支持力度。

关于更多myddd的资讯，可以随时访问官网 https://myddd.org 或微言码道 (https://taoofcoding.tech) 以了解更多。