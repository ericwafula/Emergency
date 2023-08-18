package com.emergency.emergency.domain.preferences

import com.emergency.emergency.domain.model.SavedUserDetails
import kotlinx.coroutines.flow.Flow

interface TokenProvider {
    suspend fun fetchAccessToken(): Flow<String?>
    suspend fun fetchRefreshToken(): Flow<String?>
    suspend fun update(accessToken: String, refreshToken: String, email: String = "", isLoggedIn: Boolean)
    suspend fun deleteToken()
    suspend fun updateEmail(email: String)
    suspend fun getUserEmail(): Flow<String?>
    suspend fun saveUserDetails(firstName: String, lastName: String, phoneNumber: String)
    suspend fun fetchSavedUserDetails(): Flow<SavedUserDetails>
    suspend fun saveOnBoardingState(showOnBoarding: Boolean)
    suspend fun showOnBoarding(): Flow<Boolean>
    suspend fun save401Error(hasError: Boolean)
    suspend fun fetch401Error(): Flow<Boolean>
}