package ke.co.onboarding_presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ke.co.core_ui.theme.FriendsgiftTheme
import ke.co.core_ui.theme.LocalFont
import ke.co.core_ui.theme.yellow
import onboarding_presentation.R

@Composable
fun ContinueButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onClick: () -> Unit,
    isSignup: Boolean,
    isLoading: Boolean
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = if (isSignup) Color.White else yellow)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(12.dp),
                    strokeWidth = 1.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = stringResource(id = text),
                fontSize = 12.sp,
                fontFamily = LocalFont.current.montserrat,
                color = if (isSignup) Color.Gray else Color.White
            )
        }
    }
}

@Preview
@Composable
fun ContinueButtonPreview() {
    FriendsgiftTheme {
        ContinueButton(
            text = R.string.Continue,
            onClick = { },
            isSignup = true,
            isLoading = true
        )
    }
}