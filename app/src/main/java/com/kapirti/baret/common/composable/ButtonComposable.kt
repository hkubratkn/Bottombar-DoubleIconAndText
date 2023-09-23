package com.kapirti.baret.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) { Text(text = stringResource(text)) }
}


@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, enabled: Boolean = true, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = modifier,
        enabled = enabled,
        colors =
        ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        if (enabled) {
            Text(text = stringResource(text), fontSize = 16.sp)
        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .size(36.dp)
            )
        }
    }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(text))
    }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = stringResource(text))
    }
}

private enum class Visibility {
    VISIBLE,
    GONE
}

@Composable
fun JumpToButton(
    @StringRes text: Int,
    icon: ImageVector,
    enabled: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(
        if (enabled) Visibility.VISIBLE else Visibility.GONE,
        label = "JumpToBottom visibility animation"
    )
    val bottomOffset by transition.animateDp(label = "JumpToBottom offset animation") {
        if (it == Visibility.GONE) {
            (-32).dp
        } else {
            32.dp
        }
    }
    if (bottomOffset > 0.dp) {
        ExtendedFloatingActionButton(
            icon = {
                Icon(
                    imageVector = icon,
                    modifier = Modifier.height(18.dp),
                    contentDescription = null
                )
            },
            text = {
                Text(text = stringResource(text))
            },
            onClick = onClicked,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .offset(x = 0.dp, y = -bottomOffset)
                .height(36.dp)
        )
    }
}