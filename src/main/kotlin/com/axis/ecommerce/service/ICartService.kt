package com.axis.ecommerce.service

import com.axis.ecommerce.entity.Product
import com.axis.ecommerce.entity.ShoppingCart

interface ICartService {
    fun add(productId: String,buyerId:String):ShoppingCart
    fun get(buyerId:String):ShoppingCart
    fun removeProduct(productId:String,buyerId: String):ShoppingCart
}