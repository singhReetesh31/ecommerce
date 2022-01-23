package com.axis.ecommerce.repository

import com.axis.ecommerce.entity.Order
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IOrderRepository:MongoRepository<Order,String> {
    @Query("{'product.sellerId': ?0}")
fun findBySellerId(sellerId:String):List<Order>

fun findByBuyerId(buyerId:String):List<Order>
}