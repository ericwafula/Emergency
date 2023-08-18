package com.emergency.emergency.data.dto

import com.google.gson.annotations.SerializedName

data class AuthResponseDto (
    @SerializedName("Token")
    val token: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)