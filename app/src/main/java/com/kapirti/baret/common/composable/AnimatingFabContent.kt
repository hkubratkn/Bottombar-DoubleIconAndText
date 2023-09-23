package com.kapirti.baret.common.composable

import androidx.annotation.StringRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.roundToInt

@Composable
fun FabAnimation(
    @StringRes text: Int,
    icon: ImageVector,
    extended: Boolean,
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit = { }
) {
    key(true) {
        FloatingActionButton(
            onClick = onFabClicked,
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            AnimatingFabContent(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                },
                text = {
                    Text(
                        text = stringResource(text),
                    )
                },
                extended = extended
            )
        }
    }
}

@Composable
private fun AnimatingFabContent(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    extended: Boolean = true
) {
    val currentState = if (extended) ExpandableFabStates.Extended else ExpandableFabStates.Collapsed
    val transition = updateTransition(currentState, "fab_transition")

    val textOpacity by transition.animateFloat(
        transitionSpec = {
            if (targetState == ExpandableFabStates.Collapsed) {
                tween(
                    easing = LinearEasing,
                    durationMillis = (transitionDuration / 12f * 5).roundToInt()
                )
            } else {
                tween(
                    easing = LinearEasing,
                    delayMillis = (transitionDuration / 3f).roundToInt(),
                    durationMillis = (transitionDuration / 12f * 5).roundToInt()
                )
            }
        },
        label = "fab_text_opacity"
    ) { state ->
        if (state == ExpandableFabStates.Collapsed) {
            0f
        } else {
            1f
        }
    }
    val fabWidthFactor by transition.animateFloat(
        transitionSpec = {
            if (targetState == ExpandableFabStates.Collapsed) {
                tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = transitionDuration
                )
            } else {
                tween(
                    easing = FastOutSlowInEasing,
                    durationMillis = transitionDuration
                )
            }
        },
        label = "fab_width_factor"
    ) { state ->
        if (state == ExpandableFabStates.Collapsed) {
            0f
        } else {
            1f
        }
    }
    IconAndTextRow(
        icon,
        text,
        { textOpacity },
        { fabWidthFactor },
        modifier = modifier
    )
}

@Composable
private fun IconAndTextRow(
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    opacityProgress: () -> Float,
    widthProgress: () -> Float,
    modifier: Modifier
) {
    Layout(
        modifier = modifier,
        content = {
            icon()
            Box(modifier = Modifier.graphicsLayer { alpha = opacityProgress() }) {
                text()
            }
        }
    ) { measurables, constraints ->

        val iconPlaceable = measurables[0].measure(constraints)
        val textPlaceable = measurables[1].measure(constraints)

        val height = constraints.maxHeight

        val initialWidth = height.toFloat()

        val iconPadding = (initialWidth - iconPlaceable.width) / 2f

        val expandedWidth = iconPlaceable.width + textPlaceable.width + iconPadding * 3

        val width = lerp(initialWidth, expandedWidth, widthProgress())

        layout(width.roundToInt(), height) {
            iconPlaceable.place(
                iconPadding.roundToInt(),
                constraints.maxHeight / 2 - iconPlaceable.height / 2
            )
            textPlaceable.place(
                (iconPlaceable.width + iconPadding * 2).roundToInt(),
                constraints.maxHeight / 2 - textPlaceable.height / 2
            )
        }
    }
}

private enum class ExpandableFabStates { Collapsed, Extended }
private const val transitionDuration = 200