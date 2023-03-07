package org.myddd.kotlin

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.myddd.domain.InstanceFactory
import org.myddd.ioc.spring.SpringInstanceProvider
import org.myddd.kotlin.api.dto.UserDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import jakarta.inject.Inject

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractControllerTest {


    @Value("\${server.servlet.context-path:/}")
    var contextPath: String? = null

    @Value("\${local.server.port}")
    var port = 0

    @Inject
    lateinit var restTemplate: TestRestTemplate

    @Inject
    var applicationContext: ApplicationContext? = null

    @BeforeEach
    fun beforeEach() {
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext))
    }

    companion object {

        @JvmStatic
        protected fun createHttpEntityFromString(json: String): HttpEntity<String> {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            return HttpEntity(json, headers)
        }

        protected fun createEmptyHttpEntity(): HttpEntity<Void?> {
            return HttpEntity(HttpHeaders())
        }

        @JvmStatic
        protected fun randomString(): String {
            return UUID.randomUUID().toString().replace("-".toRegex(), "")
        }

        fun randomUserDTO(): UserDTO {
            return UserDTO(userId = randomString(), name = randomString(), phone = "18620501006", email = "${randomString()}@taoofcoding.tech")
        }
    }

    fun baseUrl(): String {
        return "http://localhost:$port$contextPath"
    }
}