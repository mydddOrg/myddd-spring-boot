package org.myddd.kotlin.domain

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.myddd.kotlin.AbstractTest
import jakarta.transaction.Transactional

class UserTest:AbstractTest() {

    @Test
    @Transactional
    fun createUser(){
        val randomUser = randomUser()
        Assertions.assertNotNull(randomUser.createUser())
    }
}