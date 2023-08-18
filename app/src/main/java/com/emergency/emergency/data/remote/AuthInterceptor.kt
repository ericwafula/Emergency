package com.emergency.emergency.data.remote

import com.emergency.emergency.domain.preferences.TokenProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class AuthInterceptor @Inject constructor (
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val accessToken = tokenProvider.fetchAccessToken().firstOrNull() ?: ""
        Timber.tag("AUTH_INTERCEPTOR").d("AuthInterceptor intercept")

        val newRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()
        chain.proceed(newRequest)
    }
}