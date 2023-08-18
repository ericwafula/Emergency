package com.emergency.emergency.domain.use_case

import com.emergency.emergency.domain.model.AuthResponse
import com.emergency.emergency.domain.model.SignupRequest
import com.emergency.emergency.domain.repository.AuthRepository
import com.emergency.emergency.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Signup @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        dateOfBirth: String,
        password: String
    ): Flow<Resource<AuthResponse>> {
        val signupRequest = SignupRequest(
            firstName,
            lastName,
            email,
            phoneNumber,
            dateOfBirth,
            password
        )
        return repository.signup(signupRequest)
    }
}