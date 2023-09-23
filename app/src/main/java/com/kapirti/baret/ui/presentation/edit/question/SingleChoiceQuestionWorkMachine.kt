package com.kapirti.baret.ui.presentation.edit.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SingleChoiceQuestionWorkMachine(
    possibleAnswers: List<String>,
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .selectableGroup()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(32.dp))

        possibleAnswers.forEach {
            val selected = it == selectedAnswer
            RadioButtonWithIconRow(
                modifier = Modifier.padding(vertical = 8.dp),
                text = it,
                icon = Icons.Default.DirectionsCar,
                selected = selected,
                onOptionSelected = { onOptionSelected(it) }
            )
        }
    }
}