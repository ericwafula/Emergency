package com.emergency.emergency.core.use_case

class ValidatePhoneNumber {
    operator fun invoke(phoneNumber: String): Boolean {
        val regex0 = "^0\\d{9}$".toRegex()
        val regex254 = "^254\\d{9}$".toRegex()

        return regex0.matches(phoneNumber) || regex254.matches(phoneNumber)
    }
}