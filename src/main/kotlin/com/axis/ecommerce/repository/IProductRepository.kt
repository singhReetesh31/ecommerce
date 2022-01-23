package com.axis.ecommerce.repository

import com.axis.ecommerce.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface IProductRepository:MongoRepository<Product,String> {
}