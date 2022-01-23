package com.axis.ecommerce.repository

import com.axis.ecommerce.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*


interface IUserRepository:MongoRepository<User,String> {

fun findByUserName(userName:String):Optional<User>
fun existsByUserName(userName: String):Boolean
fun existsByEmail(email:String):Boolean

}