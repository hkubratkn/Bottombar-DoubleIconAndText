package com.kapirti.baret.ui.presentation.profile

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.baret.core.constants.Constants.EDIT_SCREEN
import com.kapirti.baret.core.constants.Constants.LOG_IN_SCREEN
import com.kapirti.baret.core.constants.Constants.REGISTER_SCREEN
import com.kapirti.baret.core.constants.Constants.SETTINGS_SCREEN
import com.kapirti.baret.core.constants.EditType.CONTACT_DESCRIPTION
import com.kapirti.baret.core.constants.EditType.DISPLAY_NAME
import com.kapirti.baret.core.constants.EditType.NAME_SURNAME
import com.kapirti.baret.core.constants.EditType.PHOTO
import com.kapirti.baret.core.constants.EditType.PROFILE
import com.kapirti.baret.core.constants.ProfileState.PROFILE_AUTH
import com.kapirti.baret.core.constants.ProfileState.PROFILE_DONE
import com.kapirti.baret.core.constants.ProfileState.PROFILE_PROFILE
import com.kapirti.baret.core.data_store.EditRepository
import com.kapirti.baret.core.data_store.EditTypeRepository
import com.kapirti.baret.core.room.profile.Profile
import com.kapirti.baret.core.room.profile.ProfileDao
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val accountService: AccountService,
    private val editRepository: EditRepository,
    private val editTypeRepository: EditTypeRepository,
    private val profileDao: ProfileDao,
    logService: LogService,
): BaretViewModel(logService){
    val displayName = accountService.currentUserDisplayName
    val profile: Flow<Profile> = profileDao.getProfile()

    private val _profileType = mutableStateOf<String?>(null)
    val profileType: String?
        get() = _profileType.value

    fun initialize(onShowSnackbar: suspend (String, String?) -> Boolean,) {
        launchCatching{
            try {
                if (accountService.hasUser) {
                    editRepository.readEditState().collect { completeState ->
                        if (completeState) {
                            _profileType.value = PROFILE_DONE
                        } else {
                            _profileType.value = PROFILE_PROFILE
                        }
                    }
                } else { _profileType.value = PROFILE_AUTH }
            } catch (ex: FirebaseAuthException) {
                onShowSnackbar(ex.localizedMessage ?: "", "")
                throw ex
            }
        }
    }

    fun onLogInClick(openScreen: (String) -> Unit) = openScreen(LOG_IN_SCREEN)
    fun onCreateClick(openScreen: (String) -> Unit) = openScreen(REGISTER_SCREEN)

    fun onProfileClick(openScreen: (String) -> Unit){
        launchCatching{
            editTypeRepository.saveEditTypeState(PROFILE)
            openScreen(EDIT_SCREEN)
        }
    }

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onPhotoClick(openScreen: (String) -> Unit){
        launchCatching{
            editTypeRepository.saveEditTypeState(PHOTO)
            openScreen(EDIT_SCREEN)
        }
    }
    fun onDisplayNameClick(openScreen: (String) -> Unit){
        launchCatching{
            editTypeRepository.saveEditTypeState(DISPLAY_NAME)
            openScreen(EDIT_SCREEN)
        }
    }
    fun onNameSurnameClick(openScreen: (String) -> Unit){
        launchCatching{
            editTypeRepository.saveEditTypeState(NAME_SURNAME)
            openScreen(EDIT_SCREEN)
        }
    }
    fun onContactDescriptionClick(openScreen: (String) -> Unit){
        launchCatching{
            editTypeRepository.saveEditTypeState(CONTACT_DESCRIPTION)
            openScreen(EDIT_SCREEN)
        }
    }
}