package com.kapirti.baret.ui.presentation.settings

import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.core.repository.SettingsRepository
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.mutableStateOf
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.core.data_store.EditTypeRepository
import com.kapirti.baret.core.constants.Constants.EDIT_SCREEN
import com.kapirti.baret.core.constants.Constants.SPLASH_SCREEN
import com.kapirti.baret.core.constants.EditType.FEEDBACK
import com.kapirti.baret.core.data_store.EditRepository
import com.kapirti.baret.core.room.profile.Profile
import com.kapirti.baret.core.room.profile.ProfileDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val editTypeRepository: EditTypeRepository,
    private val settingsRepository: SettingsRepository,
    private val profileDao: ProfileDao,
    private val editRepository: EditRepository,
    logService: LogService
): BaretViewModel(logService) {
    var uiState = mutableStateOf(SettingsUiState())
        private set

    private val password
        get() = uiState.value.password

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    val uiStateAuth = accountService.hasUser
    val profile: Flow<Profile> = profileDao.getProfile()

    fun share(){ launchCatching{ settingsRepository.share() } }
    fun rate(){ launchCatching{ settingsRepository.rate() } }

    fun onFeedbackClick(openScreen: (String) -> Unit){
        launchCatching{
            editTypeRepository.saveEditTypeState(FEEDBACK)
            openScreen(EDIT_SCREEN)
        }
    }

    fun onSignOutClick(restartApp: (String) -> Unit,  profile: Profile) {
        launchCatching {
            profileDao.delete(profile)
            editRepository.completeF()
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }
    fun onDeleteMyAccountClick(restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean, text: String, profile: Profile) {
        if (password.isBlank()) {
            launchCatching {
                onShowSnackbar(text, "")
            }
            return
        }

        launchCatching{
            accountService.authenticate(accountService.currentUserEmail, password)
            firestoreService.deleteAccount()
            profileDao.delete(profile)
            editRepository.completeF()
            accountService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }
}