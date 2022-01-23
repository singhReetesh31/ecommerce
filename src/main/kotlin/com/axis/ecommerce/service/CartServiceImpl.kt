package com.axis.ecommerce.service

import com.axis.ecommerce.entity.Product
import com.axis.ecommerce.entity.ShoppingCart
import com.axis.ecommerce.exception.AuthorityException
import com.axis.ecommerce.exception.IdNotFoundException
import com.axis.ecommerce.repository.ICartRepository
import com.axis.ecommerce.repository.IProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CartServiceImpl:ICartService {
    @Autowired
    lateinit var cartRepository: ICartRepository

    @Autowired
    lateinit var productRepository: IProductRepository
    override fun add(productId: String,buyerId:String): ShoppingCart {
        val product=productRepository.findById(productId).orElseThrow { IdNotFoundException("Product Id $productId not found") }
        //check for buyer has cart
        var cart=cartRepository.findByBuyerId(buyerId).orElseThrow { IdNotFoundException("Buyer Id $buyerId not found") }
        if (buyerId.equals(product.sellerId)){
            throw AuthorityException("You can't buy your own product")
        }
        cart.products.add(product)
        var products=cart.products
        var totalPrice:Double=0.0
        for (product in products){
            totalPrice+=product.price
        }
        cart.totalPrice=totalPrice
        return cartRepository.save(cart)
    }

    override fun get(buyerId: String): ShoppingCart {
       return cartRepository.findByBuyerId(buyerId).orElseThrow { IdNotFoundException("Cart Id $buyerId not found") }
    }

    override fun removeProduct(productId: String,buyerId: String):ShoppingCart {
        var cart = cartRepository.findByBuyerId(buyerId).orElseThrow { IdNotFoundException("Cart Id $buyerId not found") }
        var products=cart.products
        var productPrice:Double=0.0
        for (product in products){
            //println("${product.productId} ======= $productId")
            if (product.productId.equals(productId)) {
               // println("${product.productId} euquals $productId")
                products.remove(product)
                productPrice=product.price
                break
            }
        }
        println("not equal")
        cart.totalPrice-=productPrice
        return cartRepository.save(cart)
    }

}