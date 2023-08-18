package com.emergency.emergency.domain.model

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String
)
