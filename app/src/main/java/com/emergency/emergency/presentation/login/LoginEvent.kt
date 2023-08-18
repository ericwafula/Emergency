package com.emergency.emergency.presentation.login

sealed class LoginEvent {
    data class OnEnterEmail(var email: String) : LoginEvent()
    data class OnEnterPassword(var password: String) : LoginEvent()
    object OnClickContinueButton : LoginEvent()
    object OnClickLoginWithGoogle : LoginEvent()
    object OnDismissDialog : LoginEvent()
}
