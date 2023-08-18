package com.emergency.emergency.domain.model

data class SavedUserDetails(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var showOnBoarding: Boolean,
    var isLoggedIn: Boolean,
    var accessToken: String,
    val has401Error: Boolean
)
