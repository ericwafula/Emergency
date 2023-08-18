package com.emergency.emergency.presentation.signup

data class SignupState(
    var firstName: String = "",
    var firstNameIsError: Boolean = false,
    var firstNameErrorMessage: String = "",
    var lastName: String = "",
    var lastNameIsError: Boolean = false,
    var lastNameErrorMessage: String = "",
    var email: String = "",
    var emailIsError: Boolean = false,
    var emailErrorMessage: String = "invalid email",
    var phoneNumber: String = "",
    var phoneNumberIsError: Boolean = false,
    var phoneNumberErrorMessage: String = "invalid phone number",
    var dateOfBirth: String = "",
    var birthdayIsError: Boolean = false,
    var birthdayErrorMessage: String = "",
    var password: String = "",
    var passwordIsError: Boolean = false,
    var passwordErrorMessage: String = "invalid password",
    var loading: Boolean = false,
    var showDialog: Boolean = false,
    var dialogMessage: String = ""
)
