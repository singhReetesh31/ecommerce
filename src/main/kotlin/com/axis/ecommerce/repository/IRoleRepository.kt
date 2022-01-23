package com.axis.ecommerce.repository

import com.axis.ecommerce.dto.ERole
import com.axis.ecommerce.entity.Role
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface IRoleRepository: MongoRepository<Role,String> {
    fun findByName(name: ERole):Optional<Role>
}