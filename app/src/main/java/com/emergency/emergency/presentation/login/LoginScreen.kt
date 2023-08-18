package com.emergency.emergency.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.emergency.emergency.R
import com.emergency.emergency.presentation.components.LoginForm
import com.emergency.emergency.presentation.ui.theme.EmergencyTheme
import com.emergency.emergency.util.UiEvent
import timber.log.Timber

@Composable
fun LoginScreen(
    onClickSignup: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                UiEvent.Success -> {
                    onLoginSuccess()
                }

                else -> Unit
            }
        }
    }

    DisposableEffect(key1 = true) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                viewModel.showOnBoarding(true)
                Timber.tag("LOGIN_SCREEN").d("on_destroy")
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (viewModel.state.showDialog) {
        Dialog(onDismissRequest = { viewModel.onEvent(LoginEvent.OnDismissDialog) }) {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = viewModel.state.dialogMessage)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .weight(.5f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.creative_team_cuate),
                    contentDescription = stringResource(
                        id = R.string.onboarding_image
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 50.dp),
                    text = stringResource(id = R.string.log_in), style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
            }
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        ) {
            LoginForm(
                emailAddress = viewModel.state.email,
                onChangeEmailAddress = {
                    viewModel.onEvent(LoginEvent.OnEnterEmail(it))
                },
                emailIsError = viewModel.state.isValidEmailError,
                emailErrorMessage = viewModel.state.inValidEmailErrorMessage,
                password = viewModel.state.password,
                onChangePassword = {
                    viewModel.onEvent(LoginEvent.OnEnterPassword(it))
                },
                passwordIsError = viewModel.state.isValidPasswordError,
                passwordErrorMessage = viewModel.state.inValidPasswordErrorMessage,
                onClickContinueButton = {
                    viewModel.onEvent(LoginEvent.OnClickContinueButton)
                },
                onClickLoginWithGoogle = {
                    viewModel.onEvent(LoginEvent.OnClickLoginWithGoogle)
                },
                onClickSignup = onClickSignup,
                isLoading = viewModel.state.isLoading
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    EmergencyTheme {
        LoginScreen(
            onClickSignup = { },
            onLoginSuccess = { }
        )
    }
}