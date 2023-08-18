package com.emergency.emergency.core.use_case

class FormatPhoneNumber {
    operator fun invoke(phoneNumber: String): String {
        return if (phoneNumber.startsWith("0")) {
            "254${phoneNumber.substring(1)}"
        } else {
            phoneNumber
        }
    }
}