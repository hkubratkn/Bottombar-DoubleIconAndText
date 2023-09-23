package com.kapirti.baret.ui.presentation.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.kapirti.baret.core.Async
import com.kapirti.baret.core.WhileUiSubscribed
import com.kapirti.baret.core.constants.Constants.HOME_SCREEN
import com.kapirti.baret.core.data_store.CityRepository
import com.kapirti.baret.core.data_store.RentSellRepository
import com.kapirti.baret.core.data_store.TypeRepository
import com.kapirti.baret.model.Asset
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.kapirti.baret.R.string as AppText

data class HomeUiState(
    val items: List<Asset> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val cityRepository: CityRepository,
    private val rentSellRepository: RentSellRepository,
    private val typeRepository: TypeRepository,
    logService: LogService
): BaretViewModel(logService){
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)
    private val _isLoading = MutableStateFlow(false)
    private val _isTaskDeleted = MutableStateFlow(false)
    private val _taskAsync = firestoreService.assets
        .map { handleTask(it) }
        .catch { emit(Async.Error(AppText.loading_assets_error)) }


    private val _city = mutableStateOf<Int?>(null)
    val city: Int?
        get() = _city.value

    private val _rentSell = mutableStateOf<String?>(null)
    val rentSell: String?
        get() = _rentSell.value

    private val _type = mutableStateOf<String?>(null)
    val type: String?
        get() = _type.value


    init {
        launchCatching {
            cityRepository.getCityScore().collect { itCity ->
                _city.value = itCity
                rentSellRepository.getRentSell().collect { itRentSell ->
                    _rentSell.value = itRentSell
                    typeRepository.getType().collect{ itType ->
                        _type.value = itType
                    }
                }
            }
        }
    }


    val uiState: StateFlow<HomeUiState> = combine(
        _userMessage, _isLoading, _isTaskDeleted, _taskAsync
    ) { userMessage, isLoading, isTaskDeleted, taskAsync ->
        when (taskAsync) {
            Async.Loading -> {
                HomeUiState(isLoading = true)
            }
            is Async.Error -> {
                HomeUiState(
                    userMessage = taskAsync.errorMessage,
                    isTaskDeleted = isTaskDeleted
                )
            }
            is Async.Success -> {
                HomeUiState(
                    items = taskAsync.data,
                    isLoading = isLoading,
                    userMessage = userMessage,
                    isTaskDeleted = isTaskDeleted
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = WhileUiSubscribed,
            initialValue = HomeUiState(isLoading = true)
        )


    fun onCityChange(restartApp: (String) -> Unit, newValue: Int) {
        launchCatching {
            cityRepository.changeCity(newValue)
            restartApp(HOME_SCREEN)
        }
    }
    fun onRentSellChange(restartApp: (String) -> Unit, newValue: String) {
        launchCatching {
            rentSellRepository.changeRentSell(newValue)
            restartApp(HOME_SCREEN)
        }
    }
    fun onTypeChange(restartApp: (String) -> Unit, newValue: String) {
        launchCatching {
            typeRepository.changeType(newValue)
            restartApp(HOME_SCREEN)
        }
    }

    private fun handleTask(asset: List<Asset>): Async<List<Asset>> {
        if (asset == null) {
            return Async.Error(AppText.assets_not_found)
        }
        return Async.Success(asset)
    }
    fun refresh() {
        _isLoading.value = true
        launchCatching {
//            taskRepository.refreshTask(taskId)
            _isLoading.value = false
        }
    }

}

/**
fun onSaveClick(asset: Asset) {
launchCatching {
assetDao.insert(
AssetRoom(
title = asset.title,
description = asset.description,
price = asset.price,
photo = asset.photo,
contact = asset.contact,
uid = asset.uid
)
)
}
}
private fun onSentClick(asset: Asset) {
launchCatching {
settingsRepository.share()
}
}
 * */