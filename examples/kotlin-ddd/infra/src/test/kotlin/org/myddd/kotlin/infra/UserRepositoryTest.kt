package org.myddd.kotlin.infra

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.myddd.domain.InstanceFactory
import org.myddd.kotlin.domain.UserRepository
import org.myddd.kotlin.AbstractTest
import javax.transaction.Transactional

class UserRepositoryTest: AbstractTest() {

    private val userRepository by lazy { InstanceFactory.getInstance(UserRepository::class.java) }

    @Test
    @Transactional
    fun createUser(){
        val randomUser = randomUser()
        val createUser = userRepository.createUser(randomUser)
        Assertions.assertNotNull(createUser)
    }

    @Test
    @Transactional
    fun searchUser(){
        Assertions.assertTrue(userRepository.searchUserByName(randomString()).isEmpty())

        val createUser = userRepository.createUser(randomUser())
        Assertions.assertNotNull(createUser)
        Assertions.assertTrue(userRepository.searchUserByName(createUser!!.name).isNotEmpty())
    }
}