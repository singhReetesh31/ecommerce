package com.axis.ecommerce.controller

import com.axis.ecommerce.dto.*
import com.axis.ecommerce.entity.Role
import com.axis.ecommerce.entity.ShoppingCart
import com.axis.ecommerce.entity.User
import com.axis.ecommerce.exception.IdNotFoundException
import com.axis.ecommerce.exception.RoleNotFoundException
import com.axis.ecommerce.repository.ICartRepository
import com.axis.ecommerce.repository.IRoleRepository
import com.axis.ecommerce.repository.IUserRepository
import com.axis.ecommerce.security.JwtUtils
import com.axis.ecommerce.service.UserDetailsImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid
@CrossOrigin(origins = ["http://localhost:3000/"])
@RestController
@RequestMapping("/auth")
class AuthController {

@Autowired
lateinit var authenticationManager: AuthenticationManager

@Autowired
lateinit var userRepository: IUserRepository

@Autowired
lateinit var roleRepository: IRoleRepository

@Autowired
lateinit var encoder: PasswordEncoder

@Autowired
lateinit var jwtUtils: JwtUtils

@Autowired
lateinit var cartRepository: ICartRepository

@PostMapping("/login")
fun authenticateUser(@Valid @RequestBody loginRequest: LoginRequest):ResponseEntity<Any>{
    val authentication = authenticationManager.authenticate(
        UsernamePasswordAuthenticationToken(loginRequest.userName, loginRequest.password)
    )

    SecurityContextHolder.getContext().authentication = authentication
    val jwt = jwtUtils.generateJwtToken(authentication)

    val userDetails: UserDetailsImpl = authentication.principal as UserDetailsImpl
    val roles: List<String> = userDetails.getAuthorities().stream()
        .map { item -> item.getAuthority() }
        .collect(Collectors.toList())

    return ResponseEntity.ok(
        JwtResponse(
            userDetails.getName(),
            jwt,
            userDetails.getId()!!,
            userDetails.username,
            userDetails.getEmail(),
            userDetails.getAddress(),
            roles
        )
    )
}

@PostMapping("/signup")
fun registerUser(@Valid @RequestBody signUpRequest: SignupRequest):ResponseEntity<Any>{
    if (userRepository.existsByUserName(signUpRequest.userName)) {
        return ResponseEntity
            .badRequest()
            .body(MessageResponse("Error: Username is already taken!"))
    }
    if (userRepository.existsByEmail(signUpRequest.email)) {
        return ResponseEntity
            .badRequest()
            .body(MessageResponse("Error: Email is already in use!"))
    }

    // Create new user's account
    val user = User(
        signUpRequest.name,
        signUpRequest.userName,
        signUpRequest.email,
        encoder.encode(signUpRequest.password)
    )

    val strRoles: Set<String>? = signUpRequest.role
    val roles = mutableSetOf<Role>()

    if (strRoles == null) {
        val userRole = roleRepository.findByName(ERole.ROLE_BUYER)
            .orElseThrow { RuntimeException("Error: Role is not found.") }
        roles.add(userRole)
    } else {
        for (role in strRoles){
            when (role) {
                "admin" -> roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow { RoleNotFoundException("Error: role $role not found") })

                "seller" -> roles.add(roleRepository.findByName(ERole.ROLE_SELLER)
                    .orElseThrow { RoleNotFoundException("Error: role $role not found") })

                else -> {
                    //"role":["admin"],
                    roles.add(roleRepository.findByName(ERole.ROLE_BUYER)
                        .orElseThrow { RoleNotFoundException("Error: role $role not found") })
                }
            }

        }
    }
    user.roles=roles
   // creating shopping cart for buyer and assigning buyer id
   //val cart=cartRepository.save(ShoppingCart())
    //user.cartId= cart.cartId!!

    val userInfo=userRepository.save(user)
    cartRepository.save(ShoppingCart(userInfo.id!!))
    return ResponseEntity.ok(MessageResponse("User registered successfully!"))
}
    @PostMapping("/address/{id}")
    fun addAddress(@RequestBody address: Address,@PathVariable id:String):ResponseEntity<Any>{
        var user=userRepository.findById(id).orElseThrow { IdNotFoundException("No user found whith this ID: $id") }
        user.address=address
        return ResponseEntity(userRepository.save(user),HttpStatus.OK)
    }
}