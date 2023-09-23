package com.kapirti.baret.ui.presentation.edit.question

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.baret.common.composable.PriceField
import com.kapirti.baret.common.ext.fieldModifier
import com.kapirti.baret.ui.presentation.edit.QuestionWrapper

@Composable
fun FieldQuestionNumber(
    @StringRes titleResourceId: Int,
    @StringRes directionsResourceId: Int,
    @StringRes title: Int,
    value: Int,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    QuestionWrapper(
        modifier = modifier,
        titleResourceId = titleResourceId,
        directionsResourceId = directionsResourceId,
    ) {
        val fieldModifier = Modifier.fieldModifier()
        PriceField(title, value.toString(), onValueChange, fieldModifier)
    }
}