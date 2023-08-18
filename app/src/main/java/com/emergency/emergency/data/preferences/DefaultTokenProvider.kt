package com.emergency.emergency.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.emergency.emergency.domain.model.SavedUserDetails
import com.emergency.emergency.domain.preferences.TokenProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private object PreferenceKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val FIRST_NAME = stringPreferencesKey("first_name")
    val LAST_NAME = stringPreferencesKey("last_name")
    val PHONE_NUMBER = stringPreferencesKey("phone_number")
    val ON_BOARDING_STATE = booleanPreferencesKey("on_boarding_state")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val HAS_401_ERROR = booleanPreferencesKey("has_401_error")
}

class DefaultTokenProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenProvider {
    override suspend fun fetchAccessToken(): Flow<String?> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emptyPreferences()
                }
            }.map { preferences ->
                preferences[PreferenceKeys.ACCESS_TOKEN]
            }
    }

    override suspend fun fetchRefreshToken(): Flow<String?> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emptyPreferences()
                }
            }.map { preferences ->
                preferences[PreferenceKeys.REFRESH_TOKEN]
            }
    }

    override suspend fun update(accessToken: String, refreshToken: String, email: String, isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ACCESS_TOKEN] = accessToken
            preferences[PreferenceKeys.REFRESH_TOKEN] = refreshToken
            if (email.isNotBlank()) {
                preferences[PreferenceKeys.USER_EMAIL] = email
            }
            preferences[PreferenceKeys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    override suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.ACCESS_TOKEN)
            preferences.remove(PreferenceKeys.REFRESH_TOKEN)
            preferences.remove(PreferenceKeys.IS_LOGGED_IN)
            preferences.remove(PreferenceKeys.USER_EMAIL)
        }
    }

    override suspend fun updateEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.USER_EMAIL] = email
        }
    }

    override suspend fun getUserEmail(): Flow<String?> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emptyPreferences()
                }
            }.map { preferences ->
                preferences[PreferenceKeys.USER_EMAIL]
            }
    }

    override suspend fun saveUserDetails(firstName: String, lastName: String, phoneNumber: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.FIRST_NAME] = firstName
            preferences[PreferenceKeys.LAST_NAME] = lastName
            preferences[PreferenceKeys.PHONE_NUMBER] = phoneNumber
        }
    }

    override suspend fun fetchSavedUserDetails(): Flow<SavedUserDetails> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emptyPreferences()
                }
            }.map { preferences ->
                SavedUserDetails(
                    firstName = preferences[PreferenceKeys.FIRST_NAME] ?: "",
                    lastName = preferences[PreferenceKeys.LAST_NAME] ?: "",
                    email = preferences[PreferenceKeys.USER_EMAIL] ?: "",
                    phoneNumber = preferences[PreferenceKeys.PHONE_NUMBER] ?: "",
                    showOnBoarding = preferences[PreferenceKeys.ON_BOARDING_STATE] ?: true,
                    isLoggedIn = preferences[PreferenceKeys.IS_LOGGED_IN] ?: false,
                    accessToken = preferences[PreferenceKeys.ACCESS_TOKEN] ?: "",
                    has401Error = preferences[PreferenceKeys.HAS_401_ERROR] ?: false
                )
            }
    }

    override suspend fun saveOnBoardingState(showOnBoarding: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ON_BOARDING_STATE] = showOnBoarding
        }
    }



    override suspend fun showOnBoarding(): Flow<Boolean> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emptyPreferences()
                }
            }.map { preferences ->
                preferences[PreferenceKeys.ON_BOARDING_STATE] ?: true
            }
    }

    override suspend fun save401Error(hasError: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.HAS_401_ERROR] = hasError
        }
    }

    override suspend fun fetch401Error(): Flow<Boolean> {
        return dataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emptyPreferences()
                }
            }.map { preferences ->
                preferences[PreferenceKeys.HAS_401_ERROR] ?: false
            }
    }

}