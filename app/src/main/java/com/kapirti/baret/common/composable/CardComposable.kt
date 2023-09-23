package com.kapirti.baret.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun DangerousCardEditor(
    @StringRes title: Int,
    icon: ImageVector,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.primary, modifier)
}

@Composable
fun RegularCardEditor(
    @StringRes title: Int,
    icon: ImageVector,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colorScheme.onSurface, modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardEditor(
    @StringRes title: Int,
    icon: ImageVector,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    Card(
        modifier = modifier.background(MaterialTheme.colorScheme.onPrimary),
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Icon(imageVector = icon, contentDescription = "Icon", tint = highlightColor)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineCard(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    border: Boolean,
    highlightColor: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier,
    onEditClick: () -> Unit,
) {
    Card(
        border = BorderStroke(4.dp, if(border){MaterialTheme.colorScheme.error} else{MaterialTheme.colorScheme.onSurface}),
        modifier = modifier.background(MaterialTheme.colorScheme.onPrimary),
        onClick = onEditClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Icon(painter = painterResource(icon), contentDescription = "Icon", tint = highlightColor)
        }
    }
}