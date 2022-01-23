package com.axis.ecommerce.service

import com.axis.ecommerce.dto.OrderDetail
import com.axis.ecommerce.entity.Order
import com.axis.ecommerce.entity.ShoppingCart

interface IOrderService {
    fun generateOrder(buyerId:String):ShoppingCart
    fun getOrdersOfBuyer(buyerId:String):List<Order>
    fun getOrdersOfSeller(sellerId:String):List<OrderDetail>
    fun deleteOrder(orderId:String)
}