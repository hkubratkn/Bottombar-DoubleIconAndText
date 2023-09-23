package com.kapirti.baret.ui.presentation.add

import com.kapirti.baret.core.constants.Constants.EDIT_SCREEN
import com.kapirti.baret.core.constants.Constants.LOG_IN_SCREEN
import com.kapirti.baret.core.constants.Constants.REGISTER_SCREEN
import com.kapirti.baret.core.constants.EditType.CAR_SELL
import com.kapirti.baret.core.constants.EditType.LOCAL_SHIPPING_RENT
import com.kapirti.baret.core.constants.EditType.LOCAL_SHIPPING_SELL
import com.kapirti.baret.core.constants.EditType.WORK_MACHINE_RENT
import com.kapirti.baret.core.constants.EditType.WORK_MACHINE_SELL
import com.kapirti.baret.core.data_store.EditTypeRepository
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val accountService: AccountService,
    private val editTypeRepository: EditTypeRepository,
    logService: LogService
): BaretViewModel(logService) {
    val hasUser = accountService.hasUser
    fun onLogInClick(openScreen: (String) -> Unit) = openScreen(LOG_IN_SCREEN)
    fun onCreateClick(openScreen: (String) -> Unit) = openScreen(REGISTER_SCREEN)


    fun onCarSellClick(openScreen: (String) -> Unit) {
        launchCatching{
            editTypeRepository.saveEditTypeState(CAR_SELL)
            openScreen(EDIT_SCREEN)
        }
    }

    fun onLocalShippingSellClick(openScreen: (String) -> Unit) {
        launchCatching{
            editTypeRepository.saveEditTypeState(LOCAL_SHIPPING_SELL)
            openScreen(EDIT_SCREEN)
        }
    }
    fun onLocalShippingRentClick(openScreen: (String) -> Unit) {
        launchCatching{
            editTypeRepository.saveEditTypeState(LOCAL_SHIPPING_RENT)
            openScreen(EDIT_SCREEN)
        }
    }

    fun onWorkMachineSellClick(openScreen: (String) -> Unit) {
        launchCatching{
            editTypeRepository.saveEditTypeState(WORK_MACHINE_SELL)
            openScreen(EDIT_SCREEN)
        }
    }
    fun onWorkMachineRentClick(openScreen: (String) -> Unit) {
        launchCatching{
            editTypeRepository.saveEditTypeState(WORK_MACHINE_RENT)
            openScreen(EDIT_SCREEN)
        }
    }
}





/**

@HiltViewModel
class AddViewModel @Inject constructor(
private val accountService: AccountService,
private val editTypeRepository: EditTypeRepository,
logService: LogService
): BaretViewModel(logService) {
val hasUser = accountService.hasUser

private val _showDialog = mutableStateOf<Boolean>(false)
val showDialog: Boolean
get() = _showDialog.value

private val _dialogValue = mutableStateOf<String?>(null)
val dialogValue: String?
get() = _dialogValue.value

fun onLogInClick(openScreen: (String) -> Unit) = openScreen(Constants.LOG_IN_SCREEN)
fun onCreateClick(openScreen: (String) -> Unit) = openScreen(Constants.REGISTER_SCREEN)

fun onClick(searchCategory: SearchCategory, openScreen: (String) -> Unit){
launchCatching {
when(searchCategory.id){
CAR -> onSell(searchCategory = searchCategory, openScreen = openScreen)
BUS -> onSell(searchCategory = searchCategory, openScreen = openScreen)
TRAIN -> onSell(searchCategory = searchCategory, openScreen = openScreen)
BIKE -> onSell(searchCategory = searchCategory, openScreen = openScreen)
MOTORCYCLE -> onSell(searchCategory = searchCategory, openScreen = openScreen)
SAILING -> onSell(searchCategory = searchCategory, openScreen = openScreen)
AGRICULTURE -> onSell(searchCategory = searchCategory, openScreen = openScreen)
FLIGHT -> onSell(searchCategory = searchCategory, openScreen = openScreen)

LOCAL_SHIPPING -> onDialogOpen(searchCategory = searchCategory, openScreen = openScreen)
KEPCE -> onDialogOpen(searchCategory = searchCategory, openScreen = openScreen)
}
}
}

private fun onDialogOpen(searchCategory: SearchCategory, openScreen: (String) -> Unit){
launchCatching {
_dialogValue.value = searchCategory.id
_showDialog.value = true
}
}
fun onRentClick(openScreen: (String) -> Unit){
launchCatching {
val type = getEditTpeRent(_dialogValue.value ?: "")
editTypeRepository.saveEditTypeState(type)
openScreen(EDIT_SCREEN)
}
}
fun onSellClick(openScreen: (String) -> Unit){
launchCatching {
val type = getEditTpeSell(_dialogValue.value ?: "")
editTypeRepository.saveEditTypeState(type)
openScreen(EDIT_SCREEN)
}
}
private fun onSell(searchCategory: SearchCategory, openScreen: (String) -> Unit){
launchCatching {
editTypeRepository.saveEditTypeState(searchCategory.id)
openScreen(EDIT_SCREEN)
}
}
private fun getEditTpeRent(type: String): String{
return when(type){
LOCAL_SHIPPING -> RENT_LOCAL_SHIPPING
KEPCE -> RENT_KEPCE
else -> FEEDBACK
}
}
private fun getEditTpeSell(type: String): String{
return when(type){
LOCAL_SHIPPING -> SELL_LOCAL_SHIPPING
KEPCE -> SELL_KEPCE
else -> FEEDBACK
}
}
}*/