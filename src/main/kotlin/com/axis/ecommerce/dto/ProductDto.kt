package com.axis.ecommerce.dto

import com.axis.ecommerce.entity.Category

class ProductDto(
    var name:String,
    var categoryId: String,
    var price:Double,
    var description:String,
    var sellerId:String
) {
}