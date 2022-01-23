package com.axis.ecommerce.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Document
class Product{
    @Id
    var productId:String?=null
    var name:String=""
    var category: Category?=null
    var price:Double=0.0
    var description:String=""
    var imageUrl:String=""
    var date:String= LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    var sellerId:String=""

    constructor(){}



}