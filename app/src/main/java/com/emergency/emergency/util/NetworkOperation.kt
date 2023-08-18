package com.emergency.emergency.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend inline fun <T> safeApiCall(
    crossinline operation: suspend () -> T
): Flow<Resource<T>> = flow {
    try {
        emit(Resource.Loading())
        val response = operation.invoke()
        emit(Resource.Success(response))
    } catch (e: HttpException) {
        when (e.code()) {
            400 -> {
                emit(Resource.Error("invalid_request"))
            }

            401 -> {
                emit(Resource.Error("unauthorized"))
            }

            403 -> {
                emit(Resource.Error("access denied"))
            }

            404 -> {
                emit(Resource.Error("page not found"))
            }

            500 -> {
                emit(Resource.Error("something went wrong"))
            }

            503 -> {
                emit(Resource.Error("service unavailable"))
            }
        }
    } catch (e: SocketTimeoutException) {
        emit(Resource.Error("connection timeout"))
    } catch (e: IOException) {
        emit(Resource.Error("operation interrupted"))
    }
}