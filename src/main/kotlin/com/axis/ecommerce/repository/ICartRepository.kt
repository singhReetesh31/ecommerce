package com.axis.ecommerce.repository

import com.axis.ecommerce.entity.ShoppingCart
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ICartRepository:MongoRepository<ShoppingCart,String> {
    fun findByBuyerId(buyerId:String):Optional<ShoppingCart>
}