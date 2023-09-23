package com.kapirti.baret.ui.presentation.edit.question

import com.kapirti.baret.common.composable.BasicField
import com.kapirti.baret.common.ext.fieldModifier
import com.kapirti.baret.ui.presentation.edit.QuestionWrapper
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FieldQuestion(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    @StringRes text: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
    ) {
        val fieldModifier = Modifier.fieldModifier()
        BasicField(text, value, onValueChange, fieldModifier)
    }
}