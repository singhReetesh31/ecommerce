package com.axis.ecommerce.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(UserNameAlreadyExistException::class)
    fun UserNameAlreadyExistHandler(exception: Exception):ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(RoleNotFoundException::class)
    fun RoleNotFoundExceptionHandler(exception: Exception):ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(AlreadyExistException::class)
    fun alreadyExistExceptionHandler(exception: Exception):ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(IdNotFoundException::class)
    fun idNotFoundExceptionHandler(exception: Exception):ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.NOT_FOUND)
    }
    @ExceptionHandler(AuthorityException::class)
    fun authorityExceptionHandler(exception: Exception):ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.FORBIDDEN)
    }

}