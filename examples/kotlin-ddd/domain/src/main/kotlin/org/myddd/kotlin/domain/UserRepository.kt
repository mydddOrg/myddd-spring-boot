package org.myddd.kotlin.domain

import org.myddd.domain.AbstractRepository

interface UserRepository:AbstractRepository {
    fun createUser(user: User):User?

    fun searchUserByName(name: String):List<User>
}