package com.emergency.emergency.domain.use_case

import com.emergency.emergency.domain.model.AuthResponse
import com.emergency.emergency.domain.model.LoginRequest
import com.emergency.emergency.domain.repository.AuthRepository
import com.emergency.emergency.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Login @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<AuthResponse>> {
        val loginRequest = LoginRequest(email, password)
        return repository.login(loginRequest)
    }
}