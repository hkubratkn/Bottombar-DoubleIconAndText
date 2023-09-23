package com.kapirti.baret.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import com.kapirti.baret.core.constants.AssetType.CAR
import com.kapirti.baret.core.constants.AssetType.LOCAL_SHIPPING
import com.kapirti.baret.core.constants.AssetType.WORK_MACHINE
import com.kapirti.baret.core.constants.CarType.AUDI
import com.kapirti.baret.core.constants.CarType.FORD
import com.kapirti.baret.core.constants.CarType.MERCEDES
import com.kapirti.baret.core.constants.CarType.TOFAS
import com.kapirti.baret.core.constants.CarType.VOLVO
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_1
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_2
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_3
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_4
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_5
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_6
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_7
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_8
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_9
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_10
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_11
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_12
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_13
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_14
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_15
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_16
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_17
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_18
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_19
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_20
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_21
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_22
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_23
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_24
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_25
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_26
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_27
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_28
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_29
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_30
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_31
import com.kapirti.baret.core.constants.ConstAvatar.AVATAR_32
import com.kapirti.baret.core.constants.Constants.RENT
import com.kapirti.baret.core.constants.Constants.SELL
import com.kapirti.baret.core.constants.LocalShippingType
import com.kapirti.baret.core.constants.WorkMachineType.CAT

object BaretRepo {
    fun getAssetTypes(): List<String> = assetTypes
    fun getCitys(): List<Int> = citys
    fun getRentSell(): List<String> = rentSell
    fun getSortDefault() = sortDefault
    fun getCarTypes(): List<String> = carsTypes
    fun getLocalShippingTypes(): List<String> = localShippingTypes
    fun getWorkMachineTypes(): List<String> = workMachineTypes
    fun getAvatars(): List<String> = avatars
}

private val avatars = listOf(AVATAR_1,AVATAR_2,AVATAR_3,AVATAR_4,AVATAR_5,AVATAR_6,AVATAR_7,AVATAR_8,AVATAR_9,AVATAR_10,AVATAR_11,AVATAR_12,AVATAR_13,AVATAR_14,AVATAR_15,AVATAR_16,AVATAR_17,AVATAR_18,AVATAR_19,AVATAR_20,AVATAR_21,AVATAR_22,AVATAR_23,AVATAR_24,AVATAR_25,AVATAR_26,AVATAR_27,AVATAR_28,AVATAR_29,AVATAR_30,AVATAR_31,AVATAR_32,)
private val citys = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81)
private val carsTypes = listOf(AUDI, MERCEDES, VOLVO, TOFAS, FORD)
private val localShippingTypes = listOf(LocalShippingType.VOLVO)
private val workMachineTypes = listOf(CAT)
private val rentSell = listOf(RENT, SELL)
private val assetTypes = listOf(CAR, LOCAL_SHIPPING, WORK_MACHINE)

@Stable
class Filter(
    val name: String,
    enabled: Boolean = false,
    val icon: ImageVector? = null
) {
    val enabled = mutableStateOf(enabled)
}

val sortFilters = listOf(
    Filter(name = "Android's favorite (default)", icon = Icons.Filled.Android),
    Filter(name = "Rating", icon = Icons.Filled.Star),
    Filter(name = "Alphabetical", icon = Icons.Filled.SortByAlpha)
)
var sortDefault = sortFilters.get(0).name