package com.emergency.emergency.util

sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    object Logout : UiEvent()
    object ShareLink : UiEvent()
    object ShowDatePicker : UiEvent()
    class ShowSnackBar(val message: String) : UiEvent()
}