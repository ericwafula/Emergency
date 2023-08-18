package com.emergency.emergency.domain.use_case

data class OnboardingUseCases(
    val validateEmail: ValidateEmail,
    val validatePassword: ValidatePassword,
    val validatePhone: ValidatePhone,
    val getWordFromSentence: GetWordFromSentence,
    val login: Login,
    val signup: Signup,
    val logout: Logout
)
