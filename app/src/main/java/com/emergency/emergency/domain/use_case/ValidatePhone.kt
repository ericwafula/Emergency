package com.emergency.emergency.domain.use_case

class ValidatePhone {
    operator fun invoke(number: String): Result {
        if (number.isBlank()) return Result.Error("Invalid phone number")
        if (number.length < 10) return Result.Error("invalid phone number")
        val formattedNumber = buildString {
            if (number.startsWith('0')) {
                append("+254")
                number.forEachIndexed { index, c ->
                    if (index != 0) {
                        append(c)
                    }
                }
            } else {
                number.forEachIndexed { _, c ->
                    append(c)
                }
            }
        }
        val phoneNumberPattern = "^\\+254[1-9]\\d{8}$"
        return if (formattedNumber.matches(phoneNumberPattern.toRegex())) {
            Result.Success(formattedNumber)
        } else {
            Result.Error("invalid phone number")
        }
    }

    sealed class Result {
        data class Success(val phone: String) : Result()
        data class Error(val message: String) : Result()
    }
}