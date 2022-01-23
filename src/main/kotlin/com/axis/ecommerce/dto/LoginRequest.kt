package com.axis.ecommerce.dto

import javax.validation.constraints.NotBlank

class LoginRequest(
    @NotBlank(message = "username field can not be empty")
    var userName: String,
    @NotBlank(message = "password field can not be empty")
    var password: String
) {
}