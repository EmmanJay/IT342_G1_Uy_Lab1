package com.example.researchcenterlabact.model

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

data class LoginResponse(val message: String, val token: String)

data class RegisterResponse(
    val message: String,
    val userId: Int,
    val email: String,
    val firstName: String,
    val lastName: String
)

data class ErrorResponse(val message: String? = null)
