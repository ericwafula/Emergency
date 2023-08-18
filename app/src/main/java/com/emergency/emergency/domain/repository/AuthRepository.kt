package com.emergency.emergency.domain.repository

import com.emergency.emergency.domain.model.AuthResponse
import com.emergency.emergency.domain.model.LoginRequest
import com.emergency.emergency.domain.model.SignupRequest
import com.emergency.emergency.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Flow<Resource<AuthResponse>>
    suspend fun signup(signupRequest: SignupRequest): Flow<Resource<AuthResponse>>
    suspend fun logout() : Flow<Resource<Unit>>
}