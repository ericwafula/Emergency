package com.emergency.emergency.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emergency.emergency.domain.preferences.TokenProvider
import com.emergency.emergency.domain.use_case.OnboardingUseCases
import com.emergency.emergency.domain.use_case.ValidateEmail
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
class LoginViewModel @Inject constructor(
    private val tokenProvider: TokenProvider,
    private val onBoardingUseCases: OnboardingUseCases,
) : ViewModel() {

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var state by mutableStateOf(LoginState())
        private set

    init {
        getOnBoardingState()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEnterEmail -> {
                state = state.copy(
                    email = event.email
                )
            }

            is LoginEvent.OnEnterPassword -> {
                state = state.copy(
                    password = event.password
                )
            }

            is LoginEvent.OnClickContinueButton -> {
                val emailResult = onBoardingUseCases.validateEmail(state.email)

                state = when (emailResult) {
                    is ValidateEmail.Result.Error -> {
                        state.copy(
                            isValidEmailError = true
                        )
                    }

                    ValidateEmail.Result.Success -> {
                        state.copy(
                            isValidEmailError = false
                        )
                    }
                }

                state = when {
                    state.password.isBlank() -> state.copy(
                        isValidPasswordError = true
                    )

                    else -> state.copy(
                        isValidPasswordError = false
                    )
                }
                if (state.email.isNotBlank() && state.password.isNotBlank()) {
                    Timber.tag("LOGIN_VIEW_MODEL").d("Login")
                    login()
                }
            }

            is LoginEvent.OnClickLoginWithGoogle -> {

            }

            LoginEvent.OnDismissDialog ->  {
                state = state.copy(
                    showDialog = false
                )
            }
        }
    }

    private fun getOnBoardingState() {
        viewModelScope.launch {
            tokenProvider.showOnBoarding().collect { showOnBoarding ->
                state = state.copy(
                    showOnBoarding = showOnBoarding
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            onBoardingUseCases.login(state.email, state.password).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            showDialog = true,
                            dialogMessage = "Network Error"
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        tokenProvider.run {
                            update(
                                accessToken = result.data?.accessToken ?: "",
                                refreshToken = result.data?.refreshToken ?: "",
                                email = state.email,
                                isLoggedIn = true
                            )
                            saveOnBoardingState(false)
                        }

                        state = state.copy(isLoading = false)
                        _uiEvent.send(UiEvent.Success)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun showOnBoarding(value: Boolean) {
        viewModelScope.launch {
            tokenProvider.saveOnBoardingState(showOnBoarding = value)
        }
    }

}