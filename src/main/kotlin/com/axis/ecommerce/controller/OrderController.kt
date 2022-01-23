package com.axis.ecommerce.controller

import com.axis.ecommerce.dto.MessageResponse
import com.axis.ecommerce.entity.Order
import com.axis.ecommerce.entity.ShoppingCart
import com.axis.ecommerce.service.IOrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:3000/"])
@RestController
@RequestMapping("/order")
class OrderController {
    @Autowired
    lateinit var orderService: IOrderService
    @GetMapping("/generate/{buyerId}")
    fun order(@PathVariable buyerId:String):ResponseEntity<Any>{

         return ResponseEntity( orderService.generateOrder(buyerId),HttpStatus.OK)
    }
    @GetMapping("/myOrder/{buyerId}")
    fun getOrdersOfBuyers(@PathVariable buyerId:String):ResponseEntity<Any>{
        return ResponseEntity(orderService.getOrdersOfBuyer(buyerId),HttpStatus.OK)
    }
    @GetMapping("/getOrders/{sellerId}")
   fun getOrdersOfSeller(@PathVariable sellerId:String):ResponseEntity<Any>{
       return ResponseEntity(orderService.getOrdersOfSeller(sellerId),HttpStatus.OK)
   }
    @DeleteMapping("/delete/{orderId}")
    fun cancelOrder(@PathVariable orderId: String):ResponseEntity<Any>{
        orderService.deleteOrder(orderId)
        return ResponseEntity.ok(MessageResponse("Order is Cancelled"))
    }
}