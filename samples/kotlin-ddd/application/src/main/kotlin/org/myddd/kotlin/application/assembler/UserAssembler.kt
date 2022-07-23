package org.myddd.kotlin.application.assembler

import org.myddd.kotlin.api.dto.UserDTO
import org.myddd.kotlin.domain.User

fun User.toDTO():UserDTO {
    return UserDTO(id = id, userId = userId, name = name, phone = phone, email = email)
}

fun UserDTO.toUser():User {
    requireNotNull(userId)
    requireNotNull(name)

    val user = User()
    user.id = id
    user.userId = userId!!
    user.name = name!!
    user.phone = phone
    user.email = email
    return user
}