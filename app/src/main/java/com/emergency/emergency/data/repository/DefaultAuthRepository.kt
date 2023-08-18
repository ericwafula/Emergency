package com.emergency.emergency.data.repository

import com.emergency.emergency.data.mappers.toAuthResponse
import com.emergency.emergency.data.mappers.toLoginRequestDto
import com.emergency.emergency.data.mappers.toSignupRequestDto
import com.emergency.emergency.data.remote.AuthApi
import com.emergency.emergency.domain.model.LoginRequest
import com.emergency.emergency.domain.model.SignupRequest
import com.emergency.emergency.domain.repository.AuthRepository
import com.emergency.emergency.util.safeApiCall
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun login(loginRequest: LoginRequest) = safeApiCall {
        authApi.login(loginRequest.toLoginRequestDto()).toAuthResponse()
    }

    override suspend fun signup(signupRequest: SignupRequest) = safeApiCall {
        authApi.signup(signupRequest.toSignupRequestDto()).toAuthResponse()
    }

    override suspend fun logout() = safeApiCall {
        authApi.logout()
    }
}