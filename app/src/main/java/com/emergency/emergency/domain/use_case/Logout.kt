package com.emergency.emergency.domain.use_case

import com.emergency.emergency.domain.repository.AuthRepository
import com.emergency.emergency.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Logout @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Flow<Resource<Unit>> {
        return repository.logout()
    }
}