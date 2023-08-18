package com.emergency.emergency.domain.use_case

class ValidateEmail {
    operator fun invoke(input: String): Result {
        val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        val emailMatcher = Regex(emailPattern)
        return if (input.matches(emailMatcher)) {
            Result.Success
        } else {
            Result.Error("Invalid email")
        }
    }

    sealed class Result {
        object Success : Result()
        data class Error(val message: String) : Result()
    }
}