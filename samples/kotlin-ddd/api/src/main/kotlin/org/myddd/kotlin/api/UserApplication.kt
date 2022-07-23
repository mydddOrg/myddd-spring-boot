package org.myddd.kotlin.api

import org.myddd.kotlin.api.dto.UserDTO
import org.myddd.utils.Page

interface UserApplication {

    fun createUser(userDTO: UserDTO):UserDTO?

    fun pageSearchUser(search: String,pageIndex:Int,pageSize:Int = 50):Page<UserDTO>

}