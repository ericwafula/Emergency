package com.emergency.emergency.data.dto

data class SignupRequestDto(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val password: String
)
