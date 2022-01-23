package com.axis.ecommerce.service

import com.axis.ecommerce.entity.Order
import com.axis.ecommerce.entity.Product

interface IProductService {
    fun add(product: Product):Product
    fun getAll():List<Product>
    fun setOrderStatus(orderId:String):Order
}