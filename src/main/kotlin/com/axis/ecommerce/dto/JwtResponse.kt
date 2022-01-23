package com.axis.ecommerce.dto

class JwtResponse(
    var name: String,
var token:String?,
var id:String,
var userName:String,
var email:String,
var address: Address?=null,
var roles:List<String>
) {
    private var type:String="Bearer"
}