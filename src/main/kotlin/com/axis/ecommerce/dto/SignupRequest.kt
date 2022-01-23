package com.axis.ecommerce.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank;

class SignupRequest(
    @NotBlank(message = "name field can not be empty")
    var name: String,
    @NotBlank(message = "username field can not be empty")
    var userName: String,
    @NotBlank(message = "email field can not be empty")
    @Email
    var email: String,

    var role: Set<String>?,
    @NotBlank(message = "name field can not be empty")
    var password: String
) {

}