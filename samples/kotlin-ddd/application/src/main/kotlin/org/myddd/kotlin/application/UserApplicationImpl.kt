package org.myddd.kotlin.application

import org.myddd.domain.InstanceFactory
import org.myddd.kotlin.api.UserApplication
import org.myddd.kotlin.api.dto.UserDTO
import org.myddd.kotlin.application.assembler.toDTO
import org.myddd.kotlin.application.assembler.toUser
import org.myddd.kotlin.domain.User
import org.myddd.querychannel.QueryChannelService
import org.myddd.utils.Page
import javax.inject.Named
import javax.transaction.Transactional

@Named
open class UserApplicationImpl:UserApplication {

    private val queryChannelService by lazy { InstanceFactory.getInstance(QueryChannelService::class.java) }

    @Transactional
    override fun createUser(userDTO: UserDTO): UserDTO? {
        val user = userDTO.toUser()
        return user.createUser()?.toDTO()
    }

    override fun pageSearchUser(search: String, pageIndex: Int, pageSize: Int): Page<UserDTO> {
        val pageQuery = queryChannelService
            .createJpqlQuery("from User where name like :name",User::class.java)
            .addParameter("name","%$search%")
            .setPage(pageIndex,pageSize)
            .pagedList()

        return Page.builder(UserDTO::class.java)
            .data(pageQuery.data.map { it.toDTO() })
            .totalSize(pageQuery.resultCount)
            .pageSize(pageSize)
            .start(pageIndex.toLong())
    }
}