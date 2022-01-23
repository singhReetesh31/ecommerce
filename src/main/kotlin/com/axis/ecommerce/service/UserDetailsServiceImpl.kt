package com.axis.ecommerce.service

import com.axis.ecommerce.entity.User
import com.axis.ecommerce.repository.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl:UserDetailsService {
    @Autowired
    lateinit var userRepository:IUserRepository
    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        val user =userRepository.findByUserName(username!!)
            .orElseThrow { UsernameNotFoundException("username $username not found") }
        return UserDetailsImpl(user)
    }
}