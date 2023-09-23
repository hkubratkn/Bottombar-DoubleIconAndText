package com.kapirti.baret.ui.presentation.register

import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthException
import com.kapirti.baret.common.ext.isValidEmail
import com.kapirti.baret.common.ext.isValidPassword
import com.kapirti.baret.common.ext.passwordMatches
import com.kapirti.baret.core.constants.Constants.EDIT_SCREEN
import com.kapirti.baret.core.constants.Constants.REGISTER_SCREEN
import com.kapirti.baret.core.constants.EditType.PROFILE
import com.kapirti.baret.core.data_store.EditRepository
import com.kapirti.baret.core.data_store.EditTypeRepository
import com.kapirti.baret.model.User
import com.kapirti.baret.model.service.AccountService
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import com.kapirti.baret.R.string as AppText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val firestoreService: FirestoreService,
    private val editRepository: EditRepository,
    private val editTypeRepository: EditTypeRepository
): BaretViewModel(logService){
    var uiState = mutableStateOf(RegisterUiState())
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onButtonChange() {
        uiState.value = uiState.value.copy(button = !button)
    }

    fun onRegisterClick(openAndPopUp: (String, String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,
        emailError: String, passwordError: String, passwordMatchError: String) {
        onButtonChange()
        if (!email.isValidEmail()) {
            launchCatching{
                onShowSnackbar(emailError, "")
                onButtonChange()
            }
            return
        }

        if (!password.isValidPassword()) {
            launchCatching{
                onShowSnackbar(passwordError, "")
                onButtonChange()
            }
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            launchCatching {
                onShowSnackbar(passwordMatchError, "")
                onButtonChange()
            }
            return
        }

        launchCatching{
            try {
                accountService.linkAccount(email, password)
                firestoreService.saveUser(
                    User(
                        completed = false,
                        online = true,
                        uid = accountService.currentUserId,
                        token = "token",
                        date = Timestamp.now()
                    )
                )
                editRepository.completeF()
                editTypeRepository.saveEditTypeState(PROFILE)
                openAndPopUp(EDIT_SCREEN, REGISTER_SCREEN)
            } catch (ex: FirebaseAuthException) {
                launchCatching{
                    onShowSnackbar(ex.localizedMessage ?: "", "")
                    onButtonChange()
                }
                throw ex
            }
        }
    }
}