package com.emergency.emergency.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emergency.emergency.R
import com.emergency.emergency.presentation.ui.theme.EmergencyTheme
import ke.co.onboarding_presentation.components.ContinueButton
import ke.co.onboarding_presentation.components.PasswordInput
import ke.co.onboarding_presentation.signup.components.SignupFormDatePickerInput

@Composable
fun SignUpForm(
    firstName: String,
    lastName: String,
    onEnterFirstName: (String) -> Unit,
    onEnterLastName: (String) -> Unit,
    fullNameIsError: Boolean = false,
    fullNameErrorMessage: String = "",
    email: String,
    onEnterEmail: (String) -> Unit,
    emailIsError: Boolean = false,
    emailErrorMessage: String = "",
    phoneNumber: String,
    phoneNumberIsError: Boolean = false,
    onEnterPhoneNumber: (String) -> Unit,
    phoneNumberErrorMessage: String = "",
    birthday: String,
    birthdayIsError: Boolean = false,
    onClickEnterBirthday: () -> Unit,
    birthdayErrorMessage: String = "",
    password: String,
    passwordIsError: Boolean = false,
    onEnterPassword: (String) -> Unit,
    passwordErrorMessage: String = "",
    onClickLogin: () -> Unit,
    onClickContinueButton: () -> Unit,
    onClickSignUpWithGoogle: () -> Unit,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp, vertical = 24.dp)
    ) {
        FormInput(
            leadingIcon = R.drawable.user,
            hint = R.string.first_name,
            value = firstName,
            onValueChange = onEnterFirstName,
            isError = fullNameIsError,
            errorMessage = fullNameErrorMessage
        )
        FormInput(
            leadingIcon = R.drawable.user,
            hint = R.string.last_name,
            value = lastName,
            onValueChange = onEnterLastName,
            isError = fullNameIsError,
            errorMessage = fullNameErrorMessage
        )
        FormInput(
            leadingIcon = R.drawable.mail,
            hint = R.string.email_address,
            value = email,
            onValueChange = onEnterEmail,
            isError = emailIsError,
            errorMessage = emailErrorMessage
        )
        FormInput(
            leadingIcon = R.drawable.telephone,
            hint = R.string.phone_number,
            value = phoneNumber,
            onValueChange = onEnterPhoneNumber,
            isError = phoneNumberIsError,
            errorMessage = phoneNumberErrorMessage,
            keyboardType = KeyboardType.Phone
        )
        SignupFormDatePickerInput(
            icon = R.drawable.gift_box,
            hint = R.string.birthday,
            value = birthday,
            onValueChange = { },
            isError = birthdayIsError,
            errorMessage = birthdayErrorMessage,
            onClick = onClickEnterBirthday
        )
        PasswordInput(
            hint = R.string.password,
            value = password,
            onValueChange = onEnterPassword,
            isError = passwordIsError,
            errorMessage = passwordErrorMessage
        )
        Spacer(modifier = Modifier.height(42.dp))
        ContinueButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.75f),
            text = R.string.Continue,
            onClick = onClickContinueButton,
            isSignup = true,
            isLoading = isLoading
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(modifier = Modifier.width(78.dp), thickness = 1.dp)
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = stringResource(id = R.string.or),
                fontSize = 8.sp
            )
            Spacer(modifier = Modifier.width(30.dp))
            Divider(Modifier.width(78.dp), thickness = 1.dp)
        }
        Spacer(modifier = Modifier.height(18.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                color = Color.White,
                text = "Already have an account?",
                fontSize = 8.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(1.dp))
            Text(
                modifier = Modifier.clickable {
                    onClickLogin()
                },
                text = stringResource(id = R.string.log_in),
                fontSize = 8.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpFormPreview() {
    EmergencyTheme {
        SignUpForm(
            firstName = "",
            onEnterFirstName = { },
            lastName = "",
            onEnterLastName = { },
            email = "",
            onEnterEmail = { },
            phoneNumber = "",
            onEnterPhoneNumber = { },
            birthday = "",
            onClickEnterBirthday = { },
            password = "",
            onEnterPassword = { },
            onClickLogin = { },
            onClickContinueButton = { },
            onClickSignUpWithGoogle = { },
            isLoading = false
        )
    }
}