package ke.co.onboarding_presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ke.co.core_ui.theme.FriendsgiftTheme
import ke.co.core_ui.theme.LocalFont
import ke.co.core_ui.theme.grayFive
import ke.co.core_ui.theme.seed
import kotlinx.coroutines.launch
import onboarding_presentation.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordInput(
    @StringRes hint: Int,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: () -> Unit = { }
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    val trailingIcon = if (showPassword) {
        R.drawable.ic_show
    } else {
        R.drawable.ic_hide
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .bringIntoViewRequester(bringIntoViewRequester)
            .onFocusEvent { focusState ->
                if (focusState.isFocused) {
                    scope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.lock),
                contentDescription = stringResource(
                    id = R.string.input
                ),
                tint = if (isFocused) Color.White else grayFive
            )
        },
        visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = {
            Text(
                text = stringResource(id = hint),
                color = if (isFocused) Color.White else grayFive,
                fontSize = 12.sp,
                fontFamily = LocalFont.current.montserrat,
                fontWeight = FontWeight.Light
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = seed,
            focusedContainerColor = seed,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = grayFive,
            cursorColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            errorContainerColor = seed
        ),
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage,
                    fontFamily = LocalFont.current.montserrat,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    showPassword = !showPassword
                },
                imageVector = ImageVector.vectorResource(id = trailingIcon),
                contentDescription = stringResource(
                    id = R.string.input
                ),
                tint = if (isFocused) Color.White else grayFive
            )
        },
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordInputPreview() {
    FriendsgiftTheme {
        PasswordInput(
            hint = R.string.password,
            value = "",
            onValueChange = { },
            errorMessage = "invalid input"
        )
    }
}