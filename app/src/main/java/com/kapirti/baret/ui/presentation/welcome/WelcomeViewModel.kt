package com.kapirti.baret.ui.presentation.welcome

import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.core.data_store.OnBoardingRepository
import com.kapirti.baret.core.constants.Constants.WELCOME_SCREEN
import com.kapirti.baret.core.constants.Constants.HOME_SCREEN
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    logService: LogService,
    private val repository: OnBoardingRepository
): BaretViewModel(logService) {
    fun saveOnBoardingState(completed: Boolean, openAndPopUp: (String, String) -> Unit,) {
        launchCatching{
            repository.saveOnBoardingState(completed = completed)
            openAndPopUp(HOME_SCREEN, WELCOME_SCREEN)
        }
    }
}