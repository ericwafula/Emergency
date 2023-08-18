package com.emergency.emergency.presentation.signup

import java.time.LocalDate

sealed class SignupEvent {
    data class OnEnterFirstName(var name: String) : SignupEvent()
    data class OnEnterLastName(var name: String) : SignupEvent()
    data class OnEnterPhoneNumber(var phone: String) : SignupEvent()
    data class OnEnterEmailAddress(var email: String) : SignupEvent()
    object OnClickEnterBirthday : SignupEvent()
    data class OnSelectDate(var date: LocalDate) : SignupEvent()
    data class OnEnterPassword(var password: String) : SignupEvent()
    object OnClickContinueButton : SignupEvent()
    object OnClickSignupWithGoogle : SignupEvent()
    object OnDismissDialog : SignupEvent()
}
