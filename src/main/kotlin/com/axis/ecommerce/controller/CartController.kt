package com.axis.ecommerce.controller

import com.axis.ecommerce.dto.MessageResponse
import com.axis.ecommerce.entity.Product
import com.axis.ecommerce.repository.ICartRepository
import com.axis.ecommerce.service.ICartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = ["http://localhost:3000/"])
class CartController {
    @Autowired
    lateinit var cartService:ICartService
    @PostMapping("/add/{buyerId}")
    fun add(@RequestParam("productId") productId:String,@PathVariable buyerId:String):ResponseEntity<Any>{
       return ResponseEntity(cartService.add(productId,buyerId),HttpStatus.OK)
    }
    @PostMapping("/delete/{buyerId}")
    fun delete(@RequestParam("productId") productId:String, @PathVariable buyerId: String):ResponseEntity<Any>{
        return ResponseEntity(cartService.removeProduct(productId,buyerId),HttpStatus.OK)
    }
    @GetMapping("/get/{buyerId}")
    fun getCartDetails(@PathVariable buyerId: String):ResponseEntity<Any>{
        return ResponseEntity(cartService.get(buyerId),HttpStatus.OK)
    }
}