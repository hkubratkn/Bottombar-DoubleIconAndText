package com.kapirti.baret.ui.presentation.edit

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kapirti.baret.model.BaretRepo
import com.kapirti.baret.ui.presentation.edit.question.*
import com.kapirti.baret.R.string as AppText

@Composable
fun FieldQuestion(
    @StringRes title: Int,
    @StringRes directionsResourceId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    FieldQuestion(
        titleResourceId = title,
        directionsResourceId = directionsResourceId,
        text = title,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
    )
}

@Composable
fun FieldQuestionDouble(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes textSecond: Int,
    value: String,
    valueSecond: String,
    onValueChange: (String) -> Unit,
    onValueSecondChange: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    FieldQuestionDouble(
        titleResourceId = title,
        directionsResourceId = title,
        textFirst = text,
        textSecond = textSecond,
        valueFirst = value,
        valueSecond = valueSecond,
        onFirstChange = onValueChange,
        onSecondChange = onValueSecondChange,
        modifier = modifier,
    )
}

@Composable
fun AvatarQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    SingleChoiceQuestionAvatar(
        titleResourceId = AppText.avatar,
        directionsResourceId = AppText.select_your_avatar,
        possibleAnswers = BaretRepo.getAvatars(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier
    )
}

@Composable
fun CarTypeQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestionCar(
        possibleAnswers = BaretRepo.getCarTypes(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun LocalShippingTypeQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestionLocalShipping(
        possibleAnswers = BaretRepo.getLocalShippingTypes(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun WorkMachineTypeQuestion(
    selectedAnswer: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestionWorkMachine(
        possibleAnswers = BaretRepo.getWorkMachineTypes(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}

@Composable
fun CityQuestion(
    selectedAnswer: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceQuestionCity(
        possibleAnswers = BaretRepo.getCitys(),
        selectedAnswer = selectedAnswer,
        onOptionSelected = onOptionSelected,
        modifier = modifier,
    )
}



/**


@Composable
fun CarTypeQuestion(
selectedAnswer: String?,
onOptionSelected: (String) -> Unit,
modifier: Modifier = Modifier,
) {
SingleChoiceQuestionMachine(
titleResourceId = AppText.car,
directionsResourceId = AppText.select_one,
possibleAnswers = listOf(AUDI, MERCEDES),
selectedAnswer = selectedAnswer,
onOptionSelected = onOptionSelected,
modifier = modifier,
)
}

@Composable
fun CarDateQuestion(
value: String?,
onValueChange: (Long) -> Unit,
modifier: Modifier = Modifier,
) {
DateQuestion(
titleResourceId = AppText.car_date,
directionsResourceId = AppText.select_date,
text = AppText.car_date,
value = value,
onValueChange = onValueChange,
modifier = modifier,
)

@Composable
fun CarMilesQuestion(
carMiles: Int,
onValueChange: (String) -> Unit,
modifier: Modifier = Modifier,
) {
FieldQuestionNumber(
titleResourceId = AppText.car_miles,
directionsResourceId = AppText.car_miles,
title = AppText.car_miles,
value = carMiles,
onValueChange = onValueChange,
modifier = modifier,
)
}

@Composable
fun PriceQuestion(
price: Int,
onPriceChange: (String) -> Unit,
modifier: Modifier = Modifier,
) {
FieldQuestionNumber(
titleResourceId = AppText.price,
directionsResourceId = AppText.price,
title = AppText.price,
value = price,
onValueChange = onPriceChange,
modifier = modifier,
)
}

@Composable
fun DescriptionQuestion(
description: String,
onDescriptionChange: (String) -> Unit,
modifier: Modifier = Modifier,
){
FieldQuestion(
titleResourceId = AppText.description,
directionsResourceId = AppText.description,
text = AppText.description,
value = description,
onValueChange = onDescriptionChange,
modifier = modifier,
)
}



@Composable
fun CityQuestion(
selectedAnswer: Int?,
onOptionSelected: (Int) -> Unit,
modifier: Modifier = Modifier,
) {
SingleChoiceQuestionCity(
possibleAnswers = listOf(
1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,
41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,
78,79,80,81
),
selectedAnswer = selectedAnswer,
onOptionSelected = onOptionSelected,
modifier = modifier,
)
}



}*/
@Composable
fun TakeSelfieQuestion(
    imageUri: Uri?,
    onPhotoTaken: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    PhotoQuestion(
        titleResourceId = AppText.selfie_skills,
        imageUri = imageUri,
        onPhotoTaken = onPhotoTaken,
        modifier = modifier,
    )
}