package com.emergency.emergency.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emergency.emergency.core.use_case.CoreUseCases
import com.emergency.emergency.domain.preferences.TokenProvider
import com.emergency.emergency.domain.use_case.OnboardingUseCases
import com.emergency.emergency.domain.use_case.ValidateEmail
import com.emergency.emergency.domain.use_case.ValidatePassword
import com.emergency.emergency.domain.use_case.ValidatePhone
import com.emergency.emergency.util.Resource
import com.emergency.emergency.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val onboardingUseCases: OnboardingUseCases,
    private val coreUseCases: CoreUseCases
) : ViewModel() {

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(SignupState())
        private set

    fun onEvent(event: SignupEvent) {
        when (event) {
            is SignupEvent.OnEnterFirstName -> {
                state = state.copy(
                    firstName = event.name
                )
            }

            is SignupEvent.OnEnterLastName -> {
                state = state.copy(
                    lastName = event.name
                )
            }

            is SignupEvent.OnEnterEmailAddress -> {
                state = state.copy(
                    email = event.email
                )
            }

            is SignupEvent.OnEnterPhoneNumber -> {
                state = state.copy(
                    phoneNumber = event.phone
                )
            }

            is SignupEvent.OnClickEnterBirthday -> {
                viewModelScope.launch {
                    Timber.tag("SIGNUP_VIEW_MODEL").d("show date picker")
                    _uiEvent.send(UiEvent.ShowDatePicker)
                }
            }

            is SignupEvent.OnSelectDate -> {
                state = state.copy(
                    dateOfBirth = coreUseCases.localDateToString(event.date)
                )
            }

            is SignupEvent.OnEnterPassword -> {
                state = state.copy(
                    password = event.password
                )
            }

            is SignupEvent.OnClickContinueButton -> {
                val emailResult = onboardingUseCases.validateEmail(state.email)
                val passwordResult = onboardingUseCases.validatePassword(state.password)
                val phoneResult = onboardingUseCases.validatePhone(state.phoneNumber)

                state = when (emailResult) {
                    is ValidateEmail.Result.Success -> {
                        state.copy(emailIsError = false)
                    }

                    is ValidateEmail.Result.Error -> {
                        state.copy(
                            emailIsError = true
                        )
                    }
                }

                state = when (passwordResult) {
                    is ValidatePassword.Result.Success -> {
                        state.copy(passwordIsError = false)
                    }

                    is ValidatePassword.Result.Error -> {
                        state.copy(
                            passwordIsError = true
                        )
                    }
                }

                state = when (phoneResult) {
                    is ValidatePhone.Result.Success -> {
                        state.copy(
                            phoneNumberIsError = false
                        )
                    }

                    is ValidatePhone.Result.Error -> {
                        state.copy(
                            phoneNumberIsError = true
                        )
                    }
                }

                state = if (state.firstName.isBlank()) {
                    state.copy(
                        firstNameIsError = true
                    )
                } else {
                    state.copy(firstNameIsError = false)
                }

                state = if (state.dateOfBirth.isBlank()) {
                    state.copy(
                        birthdayIsError = true
                    )
                } else {
                    state.copy(birthdayIsError = false)
                }

                if (!state.firstNameIsError && !state.lastNameIsError && !state.emailIsError && !state.phoneNumberIsError && !state.birthdayIsError && !state.passwordIsError) {
                    Timber.tag("SIGNUP_VIEW_MODEL").d("saving signup info...")
                    viewModelScope.launch {
                        signup()
                    }
                }
            }

            is SignupEvent.OnClickSignupWithGoogle -> {

            }

            SignupEvent.OnDismissDialog -> {
                state = state.copy(
                    showDialog = false
                )
            }
        }
    }

    private suspend fun signup() {
        onboardingUseCases.signup(
            firstName = state.firstName,
            lastName = state.lastName,
            email = state.email,
            phoneNumber = state.phoneNumber,
            dateOfBirth = state.dateOfBirth,
            password = state.password
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    tokenProvider.run {
                        saveOnBoardingState(showOnBoarding = false)
                        update(
                            accessToken = result.data?.accessToken!!,
                            refreshToken = result.data?.refreshToken!!,
                            email = state.email,
                            isLoggedIn = true
                        ).run {
                            _uiEvent.send(UiEvent.Success)
                            state = state.copy(loading = false)
                        }
                    }
                }

                is Resource.Error -> {
                        state = state.copy(
                            loading = false,
                            showDialog = true,
                            dialogMessage = "Unable to login"
                        )
                }

                is Resource.Loading -> {
                    state = state.copy(loading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}