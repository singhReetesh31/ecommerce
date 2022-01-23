package com.axis.ecommerce.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
class Category(
   @Id
   var categoryId:String?,
   var name:String
) {
}