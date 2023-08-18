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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emergency.emergency.R
import com.emergency.emergency.presentation.ui.theme.EmergencyTheme
import ke.co.onboarding_presentation.components.ContinueButton
import ke.co.onboarding_presentation.components.PasswordInput

@Composable
fun LoginForm(
    emailAddress: String,
    onChangeEmailAddress: (String) -> Unit,
    emailIsError: Boolean = false,
    emailErrorMessage: String,
    password: String,
    onChangePassword: (String) -> Unit,
    passwordIsError: Boolean,
    passwordErrorMessage: String,
    onClickSignup: () -> Unit,
    onClickContinueButton: () -> Unit,
    onClickLoginWithGoogle: () -> Unit,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 48.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        FormInput(
            leadingIcon = R.drawable.mail,
            hint = R.string.email_address,
            value = emailAddress,
            onValueChange = onChangeEmailAddress,
            isError = emailIsError,
            errorMessage = emailErrorMessage
        )
        PasswordInput(
            hint = R.string.password,
            value = password,
            onValueChange = onChangePassword,
            isError = passwordIsError,
            errorMessage = passwordErrorMessage
        )
        Spacer(modifier = Modifier.height(24.dp))
        ContinueButton(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.75f),
            text = R.string.Continue,
            onClick = onClickContinueButton,
            isSignup = false,
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
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                color = Color.White,
                text = "Don't have an account?",
                fontSize = 8.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(1.dp))
            Text(
                modifier = Modifier.clickable {
                    onClickSignup()
                },
                text = stringResource(id = R.string.sign_up),
                fontSize = 8.sp,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(42.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    EmergencyTheme {
        LoginForm(
            emailAddress = "",
            onChangeEmailAddress = { },
            emailIsError = false,
            emailErrorMessage = "",
            password = "",
            onChangePassword = { },
            passwordIsError = false,
            passwordErrorMessage = "",
            onClickSignup = { },
            onClickContinueButton = { },
            onClickLoginWithGoogle = { }
        )
    }
}