package com.emergency.emergency.data.remote

import com.emergency.emergency.data.dto.AuthResponseDto
import com.emergency.emergency.domain.preferences.TokenProvider
import com.emergency.emergency.util.TokenResource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class AuthAuthenticator @Inject constructor (
    private val tokenProvider: TokenProvider,
    private val authApi: AuthApi
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            Timber.tag("AUTH_AUTHENTICATOR").d("Auth Authenticator: Authenticate ->")
            when (val resource = refreshAccessToken()) {
                is TokenResource.Success -> {
                    Timber.tag("AUTH_AUTHENTICATOR").d("TokenResource.Success ->")
                    tokenProvider.update(
                        accessToken = resource.data?.token ?: "",
                        refreshToken = resource.data?.refreshToken ?: "",
                        isLoggedIn = true
                    )
                    response.request.newBuilder()
                        .addHeader("Authorization", "Bearer ${resource.data?.token}")
                        .build()
                }

                is TokenResource.Error -> {
                    Timber.tag("AUTH_AUTHENTICATOR").d("TokenResource.Error ->")
                    tokenProvider.save401Error(true)
                    tokenProvider.deleteToken()
                    authApi.logout()
                    null
                }
            }
        }
    }

    private suspend fun refreshAccessToken(): TokenResource<AuthResponseDto> {
        val refreshToken = tokenProvider.fetchRefreshToken().first() ?: ""

        return try {
            val response = authApi.refreshAccessToken(refreshToken)
            TokenResource.Success(response)
        } catch (e: Exception) {
            TokenResource.Error("Unable to authenticate")
        }
    }
}