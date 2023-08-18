package com.emergency.emergency.data.remote

import com.emergency.emergency.data.dto.AuthResponseDto
import com.emergency.emergency.data.dto.LoginRequestDto
import com.emergency.emergency.data.dto.SignupRequestDto
import com.emergency.emergency.util.AuthEndpoints
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST(AuthEndpoints.SIGNUP)
    suspend fun signup(@Body signupRequestDto: SignupRequestDto): AuthResponseDto
    @POST(AuthEndpoints.LOGIN)
    suspend fun login(@Body loginRequestDto: LoginRequestDto): AuthResponseDto
    @POST(AuthEndpoints.REFRESH_TOKEN)
    suspend fun refreshAccessToken(@Body refreshToken: String): AuthResponseDto
    @POST(AuthEndpoints.LOGOUT)
    suspend fun logout()
}