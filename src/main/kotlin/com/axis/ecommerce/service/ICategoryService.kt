package com.axis.ecommerce.service

import com.axis.ecommerce.entity.Category

interface ICategoryService {
    fun add(category: Category):Category
    fun delete(id:String)
    fun getById(id: String):Category
    fun getAll():List<Category>
}