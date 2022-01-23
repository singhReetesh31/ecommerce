package com.axis.ecommerce.entity

import com.axis.ecommerce.dto.Buyer
import com.axis.ecommerce.dto.EOrderStatus
import com.axis.ecommerce.dto.OrderDetail
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Document
class Order(
    var buyerId:String,
    var product: Product
) {
    @Id
    var orderId:String?=null
    var orderStatus=EOrderStatus.PENDING

}