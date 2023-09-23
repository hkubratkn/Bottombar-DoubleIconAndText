package com.kapirti.baret.ui.presentation.splash

import androidx.compose.runtime.mutableStateOf
import com.kapirti.baret.core.data_store.OnBoardingRepository
import com.kapirti.baret.core.constants.Constants.SPLASH_SCREEN
import com.kapirti.baret.core.constants.Constants.WELCOME_SCREEN
import com.kapirti.baret.core.constants.Constants.HOME_SCREEN
import javax.inject.Inject
import com.kapirti.baret.model.service.ConfigurationService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val configurationService: ConfigurationService,
    private val onBoardingRepository: OnBoardingRepository,
    logService: LogService
): BaretViewModel(logService) {
    val showError = mutableStateOf(false)

    init{
        launchCatching{ configurationService.fetchConfiguration() }
    }

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        checkState(openAndPopUp = openAndPopUp)
    }

    private fun checkState(openAndPopUp: (String, String) -> Unit) {
        launchCatching(snackbar = false) {
            onBoardingRepository.readOnBoardingState().collect { completed ->
                if (completed) {
                    openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
                } else {
                    openAndPopUp(WELCOME_SCREEN, SPLASH_SCREEN)
                }
            }
        }
    }
}