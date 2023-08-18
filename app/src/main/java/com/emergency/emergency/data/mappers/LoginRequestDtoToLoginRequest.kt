package com.emergency.emergency.data.mappers

import com.emergency.emergency.data.dto.LoginRequestDto
import com.emergency.emergency.domain.model.LoginRequest

fun LoginRequestDto.toLoginRequest(): LoginRequest {
    return LoginRequest(
        email = email,
        password = password
    )
}