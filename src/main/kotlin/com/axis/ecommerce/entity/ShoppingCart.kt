package com.axis.ecommerce.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class ShoppingCart(
    var buyerId:String
){
    @Id
    var cartId:String?=null
    var totalPrice:Double=0.0
    var products= mutableSetOf<Product>()

}