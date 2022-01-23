package com.axis.ecommerce.service

import com.axis.ecommerce.dto.Buyer
import com.axis.ecommerce.dto.Contact
import com.axis.ecommerce.dto.OrderDetail
import com.axis.ecommerce.entity.Order
import com.axis.ecommerce.entity.ShoppingCart
import com.axis.ecommerce.exception.IdNotFoundException
import com.axis.ecommerce.repository.ICartRepository
import com.axis.ecommerce.repository.IOrderRepository
import com.axis.ecommerce.repository.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderServiceImpl:IOrderService {
    @Autowired
    lateinit var orderRepository: IOrderRepository
    @Autowired
    lateinit var cartRepository: ICartRepository
    @Autowired
    lateinit var userRepository:IUserRepository
    override fun generateOrder(buyerId:String):ShoppingCart {
        var cart=cartRepository.findByBuyerId(buyerId).orElseThrow { IdNotFoundException("No cart found with this Id: $buyerId") }
        val products=cart.products
        for (product in products){
            orderRepository.save(Order(cart.buyerId,product))
        }
        cart.products.clear()
        cart.totalPrice=0.0
        return cartRepository.save(cart)
    }

    override fun getOrdersOfBuyer(buyerId: String): List<Order> {
        return orderRepository.findByBuyerId(buyerId)
    }

    override fun getOrdersOfSeller(sellerId: String): List<OrderDetail> {
        var orders=orderRepository.findBySellerId(sellerId)
        var orderDetails= mutableListOf<OrderDetail>()
        for (order in orders){
            val buyer=userRepository.findById(order.buyerId).orElseThrow { IdNotFoundException("No Buyer found with cart Id:${order.buyerId}") }
            val address=buyer.address
            var contact=Contact(buyer.name,buyer.email, address?.street,address?.city,address?.state,address?.pincode)
            orderDetails.add(OrderDetail(contact,order))
        }
        return orderDetails
    }

    override fun deleteOrder(orderId: String) {
        orderRepository.findById(orderId).orElseThrow { IdNotFoundException("Order Id: $orderId not found!") }
        orderRepository.deleteById(orderId);
    }


}