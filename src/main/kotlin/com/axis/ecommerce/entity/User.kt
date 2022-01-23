package com.axis.ecommerce.entity

import com.axis.ecommerce.dto.Address
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty


@Document(collection = "users")
class User{
    @Id
    var id:String?=null
    @NotEmpty(message = "email field can not be empty")
    var name:String=""
    @NotEmpty(message = "email field can not be empty")
    var userName:String=""
    @NotEmpty(message = "email field can not be empty")
    var email:String=""
    @NotEmpty(message = "email field can not be empty")
    var password:String=""
    var address:Address?=null
     var roles = mutableSetOf<Role>()

    constructor(name:String,userName: String, email: String, password: String) {
        this.name=name
        this.userName = userName
        this.email = email
        this.password = password
    }

}