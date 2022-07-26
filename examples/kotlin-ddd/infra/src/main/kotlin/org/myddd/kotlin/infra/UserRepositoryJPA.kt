package org.myddd.kotlin.infra

import org.myddd.kotlin.domain.User
import org.myddd.kotlin.domain.UserRepository
import org.myddd.persistence.jpa.AbstractRepositoryJPA
import javax.inject.Named

@Named
class UserRepositoryJPA:AbstractRepositoryJPA(),UserRepository {

    override fun createUser(user: User): User?  = save(user)

    override fun searchUserByName(name: String): List<User> {
        return entityManager
            .createQuery("from User where name like :name",User::class.java)
            .setParameter("name","%$name%")
            .resultList
    }
}