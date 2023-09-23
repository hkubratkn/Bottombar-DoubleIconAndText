package com.kapirti.baret.ui.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kapirti.baret.common.composable.BaretSurface
import com.kapirti.baret.common.ext.diagonalGradientBorder
import com.kapirti.baret.common.ext.fadeInDiagonalGradientBorder
import com.kapirti.baret.common.ext.offsetGradientBackground

@Composable
fun FilterBar(
    filters: List<Int>,
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    onShowFilters: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        state = rememberLazyListState(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 12.dp, end = 8.dp),
        modifier = modifier
            .heightIn(min = 56.dp)
            .selectableGroup()
    ) {
        item {
            IconButton(onClick = onShowFilters) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null,
                    modifier = Modifier.diagonalGradientBorder(
                        colors = listOf(
                            MaterialTheme.colors.primary,
                            MaterialTheme.colors.onPrimary
                        ),
                        shape = CircleShape
                    )
                )
            }
        }
        items(filters,) {
            val selected = it == selectedAnswer
            FilterChip(
                filter = it,
                selected = selected,
                onOptionSelected = { onOptionSelected(it) },
                shape = MaterialTheme.shapes.small
            )
        }
    }
}

@Composable
private fun FilterChip(
    filter: Int,
    selected: Boolean,
    onOptionSelected: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small
) {
    val backgroundColor by animateColorAsState(
        if (selected) MaterialTheme.colors.secondary else MaterialTheme.colors.background
    )
    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = !selected,
        colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.onPrimary),
        shape = shape
    )
    val textColor by animateColorAsState(
        if (selected) Color.Black else MaterialTheme.colors.error
    )
    BaretSurface(
        modifier = modifier
            .height(28.dp)
            .selectable(
                selected,
                onClick = onOptionSelected,
                role = Role.RadioButton
            ),
        color = backgroundColor,
        contentColor = textColor,
        shape = shape,
        elevation = 2.dp
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        val pressed by interactionSource.collectIsPressedAsState()
        val backgroundPressed =
            if (pressed) {
                Modifier.offsetGradientBackground(
                    listOf(MaterialTheme.colors.primary, MaterialTheme.colors.onPrimary),
                    200f,
                    0f
                )
            } else {
                Modifier.background(Color.Transparent)
            }
        Box(
            modifier = Modifier
                .then(backgroundPressed)
                .then(border),
        ) {
            Text(
                text = filter.toString(),
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            )
        }
    }
}