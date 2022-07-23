package org.myddd.kotlin.domain

import org.myddd.domain.BaseIDEntity
import org.myddd.domain.InstanceFactory
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "user_")
class User:BaseIDEntity() {

    @Column(name = "user_id")
    lateinit var userId:String

    @javax.persistence.Transient
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