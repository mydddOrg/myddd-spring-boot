package org.myddd.kotlin.application

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.myddd.domain.InstanceFactory
import org.myddd.kotlin.AbstractTest
import org.myddd.kotlin.api.UserApplication

class UserApplicationTest:AbstractTest() {

    private val userApplication by lazy { InstanceFactory.getInstance(UserApplication::class.java) }

    @Test
    fun createUser() {
        val createdUserDTO = userApplication.createUser(randomUserDTO())
        Assertions.assertNotNull(createdUserDTO)
    }

    @Test
    fun pageSearch() {
        Assertions.assertTrue(userApplication.pageSearchUser(randomString(),0).data.isEmpty())

        val createdUserDTO = userApplication.createUser(randomUserDTO())
        requireNotNull(createdUserDTO)
        Assertions.assertTrue(userApplication.pageSearchUser(createdUserDTO.name!!,0).data.isNotEmpty())
    }
}