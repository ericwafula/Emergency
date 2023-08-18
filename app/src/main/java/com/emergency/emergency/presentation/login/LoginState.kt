package com.emergency.emergency.presentation.login

data class LoginState(
    var email: String = "",
    var isValidEmailError: Boolean = false,
    val inValidEmailErrorMessage: String = "Invalid email",
    var password: String = "",
    var isValidPasswordError: Boolean = false,
    val inValidPasswordErrorMessage: String = "Invalid password",
    var isLoading: Boolean = false,
    var showOnBoarding: Boolean = true,
    var showDialog: Boolean = false,
    var dialogMessage: String = ""
)
