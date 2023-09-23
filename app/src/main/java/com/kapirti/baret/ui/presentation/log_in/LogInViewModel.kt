package com.kapirti.baret.ui.presentation.log_in

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.common.ext.isValidEmail
import com.kapirti.baret.core.constants.Constants.PROFILE_SCREEN
import com.kapirti.baret.core.constants.EditType.PROFILE
import com.kapirti.baret.core.data_store.EditRepository
import com.kapirti.baret.core.data_store.EditTypeRepository
import com.kapirti.baret.core.room.profile.Profile
import com.kapirti.baret.core.room.profile.ProfileDao
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val editRepository: EditRepository,
    private val editTypeRepository: EditTypeRepository,
    private val profileDao: ProfileDao,
    logService: LogService,
): BaretViewModel(logService) {
    var uiState = mutableStateOf(LogInUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password
    private val button
        get() = uiState.value.button

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onButtonChange() {
        uiState.value = uiState.value.copy(button = !button)
    }

    fun onLogInClick(token: String, restartApp: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,
        emailError: String, emptyPasswordError: String) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching{
                onShowSnackbar(emailError, "")
                onButtonChange()
            }
            return
        }

        if (password.isBlank()) {
            launchCatching{
                onShowSnackbar(emptyPasswordError, "")
                onButtonChange()
            }
            return
        }

        launchCatching{
            try {
                accountService.authenticate(email, password)
                getCompletedState(token, restartApp = restartApp)
            } catch (ex: FirebaseAuthException) {
                launchCatching{
                    onShowSnackbar(ex.localizedMessage ?: "", "")
                    onButtonChange()
                }
                throw ex
            }
        }
    }

    private fun getCompletedState(token: String, restartApp: (String) -> Unit) {
        launchCatching{
            firestoreService.updateUserToken(token)
            val user = firestoreService.getUser(accountService.currentUserId)
            if(user != null) {
                if (user.completed) {
                    editRepository.completeT()
                    profileDao.insert(
                        Profile(
                            namedb = user.name,
                            surnamedb = user.surname,
                            contactdb = user.contact,
                            descriptiondb = user.description,
                            photodb = user.photo,
                        )
                    )
                    restartApp(PROFILE_SCREEN)
                } else {
                    editRepository.completeF()
                    editTypeRepository.saveEditTypeState(PROFILE)
                    restartApp(PROFILE_SCREEN)
                }
            } else {
                editTypeRepository.saveEditTypeState(PROFILE)
                restartApp(PROFILE_SCREEN)
            }
        }
    }

    fun onForgotPasswordClick(onShowSnackbar: suspend (String, String?) -> Boolean,
        emailError: String, recoveryEmailSent: String) {
        if (!email.isValidEmail()) {
            launchCatching {
                onShowSnackbar(emailError, "")
            }
            return
        }
        launchCatching {
            accountService.sendRecoveryEmail(email)
            onShowSnackbar(recoveryEmailSent, "")
        }
    }
}