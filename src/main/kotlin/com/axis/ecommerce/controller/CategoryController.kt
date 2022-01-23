package com.axis.ecommerce.controller

import com.axis.ecommerce.dto.MessageResponse
import com.axis.ecommerce.entity.Category
import com.axis.ecommerce.service.CategoryServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:3000/"])
@RestController
@RequestMapping("/category")
class CategoryController {
    @Autowired
    lateinit var categoryService: CategoryServiceImpl


    @PostMapping("/add")
    fun addCategory(@RequestBody category: Category):ResponseEntity<Any>{
       return ResponseEntity(categoryService.add(category),HttpStatus.OK)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCategory(@PathVariable id:String):ResponseEntity<Any>{
        categoryService.delete(id)
        return ResponseEntity.ok(MessageResponse("Category $id is deleted"))
    }

    @PutMapping("/edit/{id}")
    fun editCategory(@PathVariable id:String,@RequestBody category: Category):ResponseEntity<Any>{
        categoryService.getById(id)
        category.categoryId=id
       return ResponseEntity(categoryService.add(category),HttpStatus.OK)
    }

    @GetMapping("/get")
    fun getAllCategories():ResponseEntity<Any>{
      return ResponseEntity(categoryService.getAll(),HttpStatus.OK)
    }
}