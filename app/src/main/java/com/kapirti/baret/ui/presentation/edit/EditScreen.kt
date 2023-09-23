package com.kapirti.baret.ui.presentation.edit

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kapirti.baret.common.ext.supportWideScreen
import com.kapirti.baret.core.constants.Constants.CONTENT_ANIMATION_DURATION
import com.kapirti.baret.core.room.profile.Profile
import com.kapirti.baret.tokenStringDS
import com.kapirti.baret.R.string as AppText
import com.kapirti.baret.ui.theme.stronglyDeemphasizedAlpha
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EditScreen (
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val surveyScreenData = viewModel.surveyScreenData

    val profile = viewModel.profile.collectAsState(initial = Profile())
    val tokenString = stringPreferencesKey("token_string")

    val tokenStringLast = flow<String> {
        context.tokenStringDS.data.map {
            it[tokenString]
        }.collect(collector = {
            if(it != null) {
                this.emit(it)
            }
        })
    }.collectAsState(initial = "token")


    if (surveyScreenData != null) {
        SurveyQuestionsScreen(
            surveyScreenData = surveyScreenData,
            isNextEnabled = viewModel.isNextEnabled,
            onClosePressed = { popUpScreen() },
            onPreviousPressed = { viewModel.onPreviousPressed() },
            onNextPressed = { viewModel.onNextPressed() },
            onDonePressed = {
                viewModel.onDonePressed(
                    token = tokenStringLast.value,
                    context = context,
                    restartApp = restartApp,
                    profile = profile.value,
                    popUpScreen = popUpScreen
                )},
        ) { paddingValues ->
            val modifier = modifier.padding(paddingValues)

            AnimatedContent(
                targetState = surveyScreenData,
                transitionSpec = {
                    val animationSpec: TweenSpec<IntOffset> =
                        tween(CONTENT_ANIMATION_DURATION)
                    val direction = getTransitionDirection(
                        initialIndex = initialState.questionIndex,
                        targetIndex = targetState.questionIndex,
                    )
                    slideIntoContainer(
                        towards = direction,
                        animationSpec = animationSpec,
                    ) with slideOutOfContainer(
                        towards = direction,
                        animationSpec = animationSpec
                    )
                }, label = ""
            ) { targetState ->

                when (targetState.surveyQuestion) {
                    SurveyQuestion.DISPLAY_NAME -> {
                        FieldQuestion(
                            title = AppText.display_name,
                            directionsResourceId = AppText.display_name,
                            value = viewModel.displayName ?: "",
                            onValueChange = viewModel::onDisplayNameChange,
                            modifier = modifier,
                        )
                    }
                    SurveyQuestion.NAME_SURNAME -> {
                        FieldQuestionDouble(
                            title = AppText.name_and_surname,
                            text = AppText.name,
                            textSecond = AppText.surname,
                            value = viewModel.name ?: "",
                            valueSecond = viewModel.surname ?: "",
                            onValueChange = viewModel::onNameChange,
                            onValueSecondChange = viewModel::onSurnameChange,
                            modifier = modifier,
                        )
                    }
                    SurveyQuestion.CONTACT_DESCRIPTION -> FieldQuestionDouble(
                        title = AppText.contact_and_description,
                        text = AppText.contact,
                        textSecond = AppText.description,
                        value = viewModel.contact ?: "",
                        valueSecond = viewModel.description ?: "",
                        onValueChange = viewModel::onContactChange,
                        onValueSecondChange = viewModel::onDescriptionChange,
                        modifier = modifier
                    )
                    SurveyQuestion.AVATAR -> AvatarQuestion(
                        selectedAnswer = viewModel.avatar,
                        onOptionSelected = viewModel::onAvatarChange,
                        modifier = modifier
                    )


                    SurveyQuestion.CAR_TYPE -> CarTypeQuestion(
                        selectedAnswer = viewModel.displayName ?: "",
                        onOptionSelected = viewModel::onDisplayNameChange,
                        modifier = modifier
                    )
                    SurveyQuestion.LOCAL_SHIPPING_TYPE -> LocalShippingTypeQuestion(
                        selectedAnswer = viewModel.displayName ?: "",
                        onOptionSelected = viewModel::onDisplayNameChange,
                        modifier = modifier
                    )
                    SurveyQuestion.WORK_MACHINE_TYPE -> WorkMachineTypeQuestion(
                        selectedAnswer = viewModel.displayName ?: "",
                        onOptionSelected = viewModel::onDisplayNameChange,
                        modifier = modifier
                    )
                    SurveyQuestion.CITY -> CityQuestion(
                        selectedAnswer = viewModel.city ?: 34,
                        onOptionSelected = viewModel::onCityChange,
                        modifier = modifier
                    )
                    SurveyQuestion.TITLE_DESCRIPTION -> {
                        FieldQuestionDouble(
                            title = AppText.title_and_description,
                            text = AppText.title,
                            textSecond = AppText.description,
                            value = viewModel.name ?: "",
                            valueSecond = viewModel.surname ?: "",
                            onValueChange = viewModel::onNameChange,
                            onValueSecondChange = viewModel::onSurnameChange,
                            modifier = modifier,
                        )
                    }
                    SurveyQuestion.PRICE -> {
                        FieldQuestion(
                            title = AppText.price,
                            directionsResourceId = AppText.price,
                            value = viewModel.contact ?: "",
                            onValueChange = viewModel::onContactChange,
                            modifier = modifier
                        )
                    }

                    SurveyQuestion.TAKE_SELFIE -> TakeSelfieQuestion(
                        imageUri = viewModel.selfieUri,
                        onPhotoTaken = viewModel::onSelfieResponse,
                        modifier = modifier,
                    )
                    SurveyQuestion.FEEDBACK -> FieldQuestion(
                        title = AppText.feedback,
                        directionsResourceId = AppText.feedback_description,
                        value = viewModel.description ?: "",
                        onValueChange = viewModel::onDescriptionChange,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int
): AnimatedContentScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        AnimatedContentScope.SlideDirection.Left
    } else {
        AnimatedContentScope.SlideDirection.Right
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyQuestionsScreen(
    surveyScreenData: SurveyScreenData,
    isNextEnabled: Boolean,
    onClosePressed: () -> Unit,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(modifier = Modifier.supportWideScreen()) {
        Scaffold(
            topBar = {
                SurveyTopAppBar(
                    questionIndex = surveyScreenData.questionIndex,
                    totalQuestionsCount = surveyScreenData.questionCount,
                    onClosePressed = onClosePressed,
                )
            },
            content = content,
            bottomBar = {
                SurveyBottomBar(
                    shouldShowPreviousButton = surveyScreenData.shouldShowPreviousButton,
                    shouldShowDoneButton = surveyScreenData.shouldShowDoneButton,
                    isNextButtonEnabled = isNextEnabled,
                    onPreviousPressed = onPreviousPressed,
                    onNextPressed = onNextPressed,
                    onDonePressed = onDonePressed
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyTopAppBar(
    questionIndex: Int,
    totalQuestionsCount: Int,
    onClosePressed: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CenterAlignedTopAppBar(
            title = {
                TopAppBarTitle(
                    questionIndex = questionIndex,
                    totalQuestionsCount = totalQuestionsCount,
                )
            },
            actions = {
                IconButton(
                    onClick = onClosePressed,
                    modifier = Modifier.padding(4.dp)
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(stronglyDeemphasizedAlpha)
                    )
                }
            }
        )

        val animatedProgress by animateFloatAsState(
            targetValue = (questionIndex + 1) / totalQuestionsCount.toFloat(),
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        )
    }
}

@Composable
private fun SurveyBottomBar(
    shouldShowPreviousButton: Boolean,
    shouldShowDoneButton: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPressed: () -> Unit,
    onNextPressed: () -> Unit,
    onDonePressed: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 7.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            if (shouldShowPreviousButton) {
                OutlinedButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onPreviousPressed
                ) {
                    Text(text = stringResource(id = AppText.previous))
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            if (shouldShowDoneButton) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onDonePressed,
                    enabled = isNextButtonEnabled,
                ) {
                    Text(text = stringResource(id = AppText.done))
                }
            } else {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = onNextPressed,
                    enabled = isNextButtonEnabled,
                ) {
                    Text(text = stringResource(id = AppText.next))
                }
            }
        }
    }
}

@Composable
private fun TopAppBarTitle(
    questionIndex: Int,
    totalQuestionsCount: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = (questionIndex + 1).toString(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = stronglyDeemphasizedAlpha)
        )
        Text(
            text = stringResource(AppText.question_count, totalQuestionsCount),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        )
    }
}