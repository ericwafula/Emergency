package com.emergency.emergency.data.mappers

import com.emergency.emergency.data.dto.AuthResponseDto
import com.emergency.emergency.domain.model.AuthResponse

fun AuthResponseDto.toAuthResponse(): AuthResponse {
    return AuthResponse(
        accessToken = token ?: "",
        refreshToken = refreshToken ?: ""
    )
}