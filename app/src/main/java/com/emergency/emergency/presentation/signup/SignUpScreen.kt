package com.emergency.emergency.presentation.signup

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.emergency.emergency.R
import com.emergency.emergency.presentation.components.SignUpForm
import com.emergency.emergency.presentation.ui.theme.EmergencyTheme
import com.emergency.emergency.util.UiEvent
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onClickLogin: () -> Unit,
    onSignupSuccess: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val calendarState = rememberUseCaseState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.ShowDatePicker -> {
                    calendarState.show()
                }

                UiEvent.Success -> {
                    Toast.makeText(context, "Signup Was Successful", Toast.LENGTH_LONG).show()
                    onSignupSuccess()
                }

                else -> Unit
            }
        }
    }

    if (viewModel.state.showDialog) {
        Dialog(onDismissRequest = { viewModel.onEvent(SignupEvent.OnDismissDialog) }) {
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

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date { newDate ->
            viewModel.onEvent(SignupEvent.OnSelectDate(newDate))
        },
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .padding(bottom = 25.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Spacer(modifier = Modifier.height(90.dp))
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
                text = stringResource(id = R.string.sign_up), style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .fillMaxWidth()
        ) {
            SignUpForm(
                firstName = viewModel.state.firstName,
                onEnterFirstName = {
                    viewModel.onEvent(SignupEvent.OnEnterFirstName(it))
                },
                lastName = viewModel.state.lastName,
                onEnterLastName = {
                    viewModel.onEvent(SignupEvent.OnEnterLastName(it))
                },
                fullNameIsError = viewModel.state.firstNameIsError,
                fullNameErrorMessage = viewModel.state.firstNameErrorMessage,
                email = viewModel.state.email,
                onEnterEmail = {
                    viewModel.onEvent(SignupEvent.OnEnterEmailAddress(it))
                },
                emailIsError = viewModel.state.emailIsError,
                emailErrorMessage = viewModel.state.emailErrorMessage,
                phoneNumber = viewModel.state.phoneNumber,
                onEnterPhoneNumber = {
                    viewModel.onEvent(SignupEvent.OnEnterPhoneNumber(it))
                },
                phoneNumberIsError = viewModel.state.phoneNumberIsError,
                phoneNumberErrorMessage = viewModel.state.phoneNumberErrorMessage,
                birthday = viewModel.state.dateOfBirth,
                onClickEnterBirthday = {
                    viewModel.onEvent(SignupEvent.OnClickEnterBirthday)
                },
                birthdayIsError = viewModel.state.birthdayIsError,
                birthdayErrorMessage = viewModel.state.birthdayErrorMessage,
                password = viewModel.state.password,
                onEnterPassword = {
                    viewModel.onEvent(SignupEvent.OnEnterPassword(it))
                },
                passwordIsError = viewModel.state.passwordIsError,
                passwordErrorMessage = viewModel.state.passwordErrorMessage,
                onClickLogin = onClickLogin,
                onClickContinueButton = {
                    viewModel.onEvent(SignupEvent.OnClickContinueButton)
                },
                onClickSignUpWithGoogle = {
                    viewModel.onEvent(SignupEvent.OnClickSignupWithGoogle)
                },
                isLoading = viewModel.state.loading
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    EmergencyTheme {
        SignUpScreen(onClickLogin = { }, onSignupSuccess = { })
    }
}