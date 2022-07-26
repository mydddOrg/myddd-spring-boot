package org.myddd.kotlin.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.myddd.kotlin.AbstractControllerTest
import org.myddd.kotlin.api.dto.UserDTO
import org.myddd.utils.Page

class UserControllerTest:AbstractControllerTest() {

    @Test
    fun createUser(){
        val responseEntity = restTemplate.postForEntity(baseUrl() + "/v1/users", createHttpEntityFromString(randomCreateUserJSON()),UserDTO::class.java)
        Assertions.assertTrue(responseEntity.statusCode.is2xxSuccessful)
    }

    @Test
    fun searchUser(){
        var responseEntity = restTemplate.getForEntity(baseUrl() + "/v1/users?search=${randomString()}", Page::class.java)
        Assertions.assertTrue(responseEntity.body.data.isEmpty())

        responseEntity = restTemplate.getForEntity(baseUrl() + "/v1/users", Page::class.java)
        Assertions.assertTrue(responseEntity.body.data.isEmpty())
    }

    private fun randomCreateUserJSON():String {
        return """
            {
              "name":"${randomString()}",
              "userId":"${randomString()}",
              "phone":"18620501006",
              "email":"${randomString()}"
            }
        """.trimIndent()
    }

}