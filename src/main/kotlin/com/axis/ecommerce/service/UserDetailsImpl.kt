package com.axis.ecommerce.service

import com.axis.ecommerce.dto.Address
import com.axis.ecommerce.entity.User
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl:UserDetails {
private var id:String?=null
    private var name:String=""
    private var userName:String=""
    private var email:String=""
    @JsonIgnore
    private var password:String=""
    private var address:Address?=null
    private var authorities: MutableList<GrantedAuthority> = mutableListOf()
    constructor(user: User){
        this.id=user.id
        this.name=user.name
        this.userName = user.userName
        this.email=user.email
       this.password = user.password
        this.address=user.address
        //println(this.name+"========="+user.name)
        for (role in user.roles) {
           // println("==============" + role + "====" + role.name)
            authorities.add(SimpleGrantedAuthority(role.name.toString()))
        }
    }
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }
    fun getId(): String? {
        return id
    }
    fun getName():String{
        return name
    }
    fun getAddress():Address?{
        return address
    }
    fun getEmail(): String {
        return email
    }
    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}