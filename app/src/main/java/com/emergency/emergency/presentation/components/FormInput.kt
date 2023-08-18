package com.emergency.emergency.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.emergency.emergency.R
import com.emergency.emergency.presentation.ui.theme.EmergencyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    @DrawableRes leadingIcon: Int,
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

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    val visualTransformation =
        remember {
            mutableStateOf(
                VisualTransformation.None
            )
        }

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
                imageVector = ImageVector.vectorResource(id = leadingIcon),
                contentDescription = null,
            )
        },
        visualTransformation = visualTransformation.value,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        label = {
            Text(
                text = stringResource(id = hint),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
        },
        supportingText = {
            if (isError) {
                Text(
                    text = errorMessage,
                    fontSize = 8.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun FormInputPreview() {
    EmergencyTheme {
        FormInput(
            leadingIcon = R.drawable.user,
            hint = R.string.first_name,
            value = "",
            onValueChange = { },
            errorMessage = "invalid input"
        )
    }
}