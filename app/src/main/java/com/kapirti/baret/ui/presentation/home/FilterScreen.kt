package com.kapirti.baret.ui.presentation.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kapirti.baret.common.composable.BaretSurface
import com.kapirti.baret.common.ext.fadeInDiagonalGradientBorder
import com.kapirti.baret.common.ext.offsetGradientBackground
import com.kapirti.baret.model.BaretRepo
import com.kapirti.baret.R.string as AppText

//import com.google.accompanist.flowlayout.FlowMainAxisAlignment
//import com.google.accompanist.flowlayout.FlowRow

@Composable
fun FilterScreen(
    onDismiss: () -> Unit,
    selectedAnswerType: String?,
    onOptionSelectedType: (String) -> Unit,
    selectedAnswerRentSell: String?,
    onOptionSelectedRentSell: (String) -> Unit,
) {
    val rentSell by remember { mutableStateOf(BaretRepo.getRentSell()) }
    val types by remember{ mutableStateOf(BaretRepo.getAssetTypes()) }
    var sortState by remember { mutableStateOf(BaretRepo.getSortDefault()) }
    var maxCalories by remember { mutableStateOf(0f) }
    val defaultFilter = BaretRepo.getSortDefault()

    Dialog(onDismissRequest = onDismiss) {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = AppText.label_filters),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                    },
                    actions = {
                        val resetEnabled = sortState != defaultFilter
                        IconButton(
                            onClick = { /* TODO: Open search */ },
                            enabled = resetEnabled
                        ) {
                            val alpha = if (resetEnabled) {
                                ContentAlpha.high
                            } else {
                                ContentAlpha.disabled
                            }
                            CompositionLocalProvider(LocalContentAlpha provides alpha) {
                                Text(
                                    text = stringResource(id = AppText.reset),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    },
                    backgroundColor = MaterialTheme.colors.background
                )
            }
        ) {innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                TypeFilters(
                    types = types,
                    selectedAnswer = selectedAnswerType,
                    onOptionSelected = onOptionSelectedType
                )

                RentSellSection(
                    rentSell = rentSell,
                    selectedAnswer = selectedAnswerRentSell,
                    onOptionSelected = onOptionSelectedRentSell,
                )

                /**                SortFiltersSection(
                sortState = sortState,
                onFilterChange = { filter ->
                sortState = filter.name
                }
                )

                FilterChipSection(
                title = stringResource(id = R.string.category),
                filters = categoryFilters
                )

                MaxCalories(
                sliderPosition = maxCalories,
                onValueChanged = { newValue ->
                maxCalories = newValue
                }
                )
                FilterChipSection(
                title = stringResource(id = R.string.lifestyle),
                filters = lifeStyleFilters
                )*/

                /**                SortFiltersSection(
                sortState = sortState,
                onFilterChange = { filter ->
                sortState = filter.name
                }
                )

                FilterChipSection(
                title = stringResource(id = R.string.category),
                filters = categoryFilters
                )

                MaxCalories(
                sliderPosition = maxCalories,
                onValueChanged = { newValue ->
                maxCalories = newValue
                }
                )
                FilterChipSection(
                title = stringResource(id = R.string.lifestyle),
                filters = lifeStyleFilters
                )*/
            }
        }
    }
}

@Composable
private fun RentSellSection(
    rentSell: List<String>,
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
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
        items(rentSell) {
            val selected = it == selectedAnswer
            FilterChipString(
                filter = it,
                selected = selected,
                onOptionSelected = { onOptionSelected(it) },
                shape = MaterialTheme.shapes.small
            )
        }
    }
}

@Composable
private fun TypeFilters(
    types: List<String>,
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
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
        items(types) {
            val selected = it == selectedAnswer
            FilterChipString(
                filter = it,
                selected = selected,
                onOptionSelected = { onOptionSelected(it) },
                shape = MaterialTheme.shapes.small
            )
        }
    }
}

/**
//    FilterTitle(text = title)
/**
 *
 * @Composable
 * fun FilterBar(
 *     filters: List<Int>,
 *
 *     onShowFilters: () -> Unit,

 * FlowRow(
mainAxisAlignment = FlowMainAxisAlignment.Center,
modifier = Modifier
.fillMaxWidth()
.padding(top = 12.dp, bottom = 16.dp)
.padding(horizontal = 4.dp)
) {
filters.forEach { filter ->
FilterChip(
filter = filter,
modifier = Modifier.padding(end = 4.dp, bottom = 8.dp)
)
}
}*/
}

@Composable
fun SortFiltersSection(sortState: String, onFilterChange: (Filter) -> Unit) {
FilterTitle(text = stringResource(id = R.string.sort))
Column(Modifier.padding(bottom = 24.dp)) {
SortFilters(
sortState = sortState,
onChanged = onFilterChange
)
}
}

@Composable
fun SortFilters(
sortFilters: List<Filter> = SquareRepo.getSortFilters(),
sortState: String,
onChanged: (Filter) -> Unit
) {

sortFilters.forEach { filter ->
SortOption(
text = filter.name,
icon = filter.icon,
selected = sortState == filter.name,
onClickOption = {
onChanged(filter)
}
)
}
}

@Composable
fun MaxCalories(sliderPosition: Float, onValueChanged: (Float) -> Unit) {
/**    FlowRow {
FilterTitle(text = stringResource(id = R.string.max_calories))
Text(
text = stringResource(id = R.string.per_serving),
style = MaterialTheme.typography.body2,
color = MaterialTheme.colors.onError,
modifier = Modifier.padding(top = 5.dp, start = 10.dp)
)
}*/
Slider(
value = sliderPosition,
onValueChange = { newValue ->
onValueChanged(newValue)
},
valueRange = 0f..300f,
steps = 5,
modifier = Modifier
.fillMaxWidth(),
colors = SliderDefaults.colors(
thumbColor = MaterialTheme.colors.onError,
activeTrackColor = MaterialTheme.colors.onError
)
)
}

@Composable
fun FilterTitle(text: String) {
Text(
text = text,
style = MaterialTheme.typography.h6,
color = MaterialTheme.colors.onError,
modifier = Modifier.padding(bottom = 8.dp)
)
}
@Composable
fun SortOption(
text: String,
icon: ImageVector?,
onClickOption: () -> Unit,
selected: Boolean
) {
Row(
modifier = Modifier
.padding(top = 14.dp)
.selectable(selected) { onClickOption() }
) {
if (icon != null) {
Icon(imageVector = icon, contentDescription = null)
}
Text(
text = text,
style = MaterialTheme.typography.subtitle1,
modifier = Modifier
.padding(start = 10.dp)
.weight(1f)
)
if (selected) {
Icon(
imageVector = Icons.Filled.Done,
contentDescription = null,
tint = MaterialTheme.colors.onError
)
}
}
}
 */

@Composable
private fun FilterChipString(
    filter: String,
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
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                /**                .toggleable(
                value = selected,
                onValueChange = setSelected,
                interactionSource = interactionSource,
                indication = null
                )*/
                .then(backgroundPressed)
                .then(border),
        ) {
            Text(
                text = filter,
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