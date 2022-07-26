package org.myddd.kotlin.controller

import org.myddd.kotlin.api.UserApplication
import org.myddd.kotlin.api.dto.UserDTO
import org.myddd.utils.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@Controller
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE], value = ["/v1"])
class UserController {


    @Inject
    lateinit var userApplication: UserApplication

    @PostMapping("/users")
    suspend fun createUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val created = userApplication.createUser(userDTO)
        return ResponseEntity.ok(created)
    }

    @GetMapping("/users")
    suspend fun searchUser(@RequestParam(defaultValue = "") search:String,@RequestParam(defaultValue = "0") start:Int, @RequestParam(defaultValue = "100") pageSize:Int):ResponseEntity<Page<UserDTO>>{
        val queryResult = userApplication.pageSearchUser(search,start,pageSize)
        return ResponseEntity.ok(queryResult)
    }

}