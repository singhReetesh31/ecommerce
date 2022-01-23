package com.axis.ecommerce.repository

import com.axis.ecommerce.entity.Category
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ICategoryRepository:MongoRepository<Category,String> {
    fun findByName(name:String):Optional<Category>
}