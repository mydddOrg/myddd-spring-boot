package org.myddd.kotlin

import org.junit.jupiter.api.BeforeEach
import org.myddd.domain.InstanceFactory
import org.myddd.ioc.spring.SpringInstanceProvider
import org.myddd.kotlin.domain.User
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import java.util.*
import jakarta.inject.Inject

@SpringBootTest(classes = [AbstractTest::class])
@SpringBootApplication
@ComponentScan(basePackages = ["tech.taoofcoding","org.myddd"])
@EntityScan(basePackages = ["tech.taoofcoding","org.myddd"])
abstract class AbstractTest {

    @Inject
    lateinit var applicationContext:ApplicationContext

    @BeforeEach
    fun beforeEach(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext))
    }

    companion object {
        fun randomString():String {
            return UUID.randomUUID().toString().replace("-","")
        }
    }

    fun randomUser():User {
        val user = User()
        user.userId = randomString()
        user.name = randomString()
        user.phone = "18620501006"
        user.email = "${randomString()}@taoofcoding.tech"
        return user
    }
}