package com.emergency.emergency.util

internal sealed class TokenResource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : TokenResource<T>(data, null)
    class Error<T>(message: String) : TokenResource<T>(null, message)
}
