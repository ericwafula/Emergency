package com.emergency.emergency.domain.model

data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val password: String
)
