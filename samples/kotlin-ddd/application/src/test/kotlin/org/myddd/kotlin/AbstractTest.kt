package org.myddd.kotlin

import org.junit.jupiter.api.BeforeEach
import org.myddd.domain.InstanceFactory
import org.myddd.ioc.spring.SpringInstanceProvider
import org.myddd.kotlin.api.dto.UserDTO
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import java.util.*
import javax.inject.Inject

@SpringBootTest(classes = [AbstractTest::class])
@SpringBootApplication
@ComponentScan(basePackages = ["org.myddd"])
@EntityScan(basePackages = ["org.myddd"])
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

    fun randomUserDTO():UserDTO {
        return UserDTO(userId = randomString(), name = randomString(), phone = "18620501006", email = "${randomString()}@taoofcoding.tech")
    }
}