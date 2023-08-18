package com.emergency.emergency.data.mappers

import com.emergency.emergency.data.dto.SignupRequestDto
import com.emergency.emergency.domain.model.SignupRequest

fun SignupRequestDto.toSignupRequest(): SignupRequest {
    return SignupRequest(
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        dateOfBirth = dateOfBirth,
        password = password
    )
}