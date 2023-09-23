package com.kapirti.baret.ui.presentation.edit.question

import com.kapirti.baret.common.composable.BasicField
import com.kapirti.baret.common.ext.fieldModifier
import com.kapirti.baret.ui.presentation.edit.QuestionWrapper
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FieldQuestionDouble(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    @StringRes textFirst: Int,
    @StringRes textSecond: Int,
    valueFirst: String,
    valueSecond: String,
    onFirstChange: (String) -> Unit,
    onSecondChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
    ) {
        val fieldModifier = Modifier.fieldModifier()
        BasicField(textFirst, valueFirst, onFirstChange, fieldModifier)
        BasicField(textSecond, valueSecond, onSecondChange, fieldModifier)
    }
}