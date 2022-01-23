package com.axis.ecommerce.service

import com.axis.ecommerce.dto.EOrderStatus
import com.axis.ecommerce.entity.Order
import com.axis.ecommerce.entity.Product
import com.axis.ecommerce.exception.IdNotFoundException
import com.axis.ecommerce.repository.IOrderRepository
import com.axis.ecommerce.repository.IProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl:IProductService {
    @Autowired
    lateinit var productRepository: IProductRepository
    @Autowired
    lateinit var orderRepository: IOrderRepository
    override fun add(product: Product): Product {
        return productRepository.save(product)
    }

    override fun getAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun setOrderStatus(orderId: String): Order {
        var order=orderRepository.findById(orderId).orElseThrow { IdNotFoundException("Order ID: $orderId not found") }
        order.orderStatus=EOrderStatus.CONFIRMED
        return orderRepository.save(order)
    }
}