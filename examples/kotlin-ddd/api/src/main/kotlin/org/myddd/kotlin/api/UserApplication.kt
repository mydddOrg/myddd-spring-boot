package org.myddd.kotlin.api

import org.myddd.kotlin.api.dto.UserDTO
import org.myddd.utils.Page

interface UserApplication {

    suspend fun createUser(userDTO: UserDTO):UserDTO?

    suspend fun pageSearchUser(search: String,pageIndex:Int,pageSize:Int = 50):Page<UserDTO>

}