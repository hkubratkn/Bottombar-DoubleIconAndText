package com.kapirti.baret.ui.presentation.edit.question

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kapirti.baret.common.composable.NoSurfaceImage
import com.kapirti.baret.ui.presentation.edit.QuestionWrapper

@Composable
fun SingleChoiceQuestionAvatar (
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    possibleAnswers: List<String>,
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
        modifier = modifier.selectableGroup(),
    ) {
        LazyRow{
            items(possibleAnswers){
                val selected = it == selectedAnswer
                RadioButtonWithImageColumn(
                    modifier = Modifier.padding(vertical = 8.dp),
                    imageResourceId = it,
                    selected = selected,
                    onOptionSelected = { onOptionSelected(it) }
                )
            }
        }
    }
}

@Composable
private fun RadioButtonWithImageColumn(
    imageResourceId: String,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (selected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }
        ),
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.5f).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoSurfaceImage(
                imageUrl = imageResourceId,
                contentDescription = null,
                modifier = Modifier.size(100.dp).clip(MaterialTheme.shapes.extraSmall).padding(start = 0.dp, bottom = 8.dp)
            )
            Spacer(Modifier.width(8.dp))

            Box(Modifier.padding(8.dp)) {
                RadioButton(selected, onClick = null)
            }
        }
    }
}