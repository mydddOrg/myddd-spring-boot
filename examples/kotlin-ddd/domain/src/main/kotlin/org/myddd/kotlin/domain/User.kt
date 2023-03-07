package org.myddd.kotlin.domain

import org.myddd.domain.BaseIDEntity
import org.myddd.domain.InstanceFactory
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_")
class User:BaseIDEntity() {

    @Column(name = "user_id")
    lateinit var userId:String

    @jakarta.persistence.Transient
    lateinit var password:String

    @Column(name = "encode_password")
    lateinit var encodePassword:String

    lateinit var name:String

    var phone:String? = ""

    var email:String? = ""

    var disabled:Boolean = false

    companion object {
        private val userRepository by lazy { InstanceFactory.getInstance(UserRepository::class.java) }
    }

    fun createUser():User? {
        return userRepository.createUser(this)
    }
}