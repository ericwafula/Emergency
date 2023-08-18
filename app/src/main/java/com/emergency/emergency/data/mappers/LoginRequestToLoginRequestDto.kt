package com.emergency.emergency.data.mappers

import com.emergency.emergency.data.dto.LoginRequestDto
import com.emergency.emergency.domain.model.LoginRequest

fun LoginRequest.toLoginRequestDto(): LoginRequestDto {
    return LoginRequestDto(
        email = email,
        password = password
    )
}