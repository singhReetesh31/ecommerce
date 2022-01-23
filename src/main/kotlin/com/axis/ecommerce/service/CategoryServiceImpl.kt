package com.axis.ecommerce.service

import com.axis.ecommerce.entity.Category
import com.axis.ecommerce.exception.AlreadyExistException
import com.axis.ecommerce.exception.IdNotFoundException
import com.axis.ecommerce.repository.ICategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl:ICategoryService {
    @Autowired
    lateinit var categoryRepository: ICategoryRepository
    override fun add(category: Category): Category {
        if (categoryRepository.findByName(category.name).isPresent)
            throw AlreadyExistException("category name: ${category.name} already exist")
       return categoryRepository.save(category)
    }

    override fun delete(id: String) {
            getById(id)
            categoryRepository.deleteById(id)

    }

    override fun getById(id: String): Category {
        return categoryRepository.findById(id).orElseThrow { IdNotFoundException("Category ID: $id Not Found") }
    }

    override fun getAll(): List<Category> {
        return categoryRepository.findAll()
    }
}