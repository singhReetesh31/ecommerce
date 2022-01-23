package com.axis.ecommerce.entity

import com.axis.ecommerce.dto.ERole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "roles")
class Role(
    var name:ERole
) {
    @Id
    var id:String?=null
    override fun equals(other: Any?): Boolean {
        if (name==other)
            return true
        return false
    }
}