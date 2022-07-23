package org.myddd.kotlin.api.dto

import java.beans.ConstructorProperties

data class UserDTO
    @ConstructorProperties(value = ["id","userId","name","phone","email","password"])
    constructor(val id:Long? = null,val userId:String? = "",val name:String? = "",val phone:String? = "",val email:String? = "",val password:String? = "")
