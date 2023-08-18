package com.emergency.emergency.domain.use_case

class ValidatePassword {
    operator fun invoke(input: String): Result {
        /**
         * (?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d,./:'"\-+=\[\]}{|\\`~!@#$%^&*()]{8,}
         */
        val passwordPattern =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#\$%^&*()\\-_=+\\[{\\]};:'\",<.>/?\\\\|]).{8,}\$"
        val passwordMatcher = Regex(passwordPattern)
        return if (input.matches(passwordMatcher)) {
            Result.Success
        } else {
            Result.Error("Invalid password")
        }
    }

    sealed class Result {
        object Success : Result()
        data class Error(val message: String) : Result()
    }
}