package com.kapirti.baret.ui.presentation.log_in

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.baret.common.composable.*
import com.kapirti.baret.common.ext.basicButton
import com.kapirti.baret.common.ext.fieldModifier
import com.kapirti.baret.common.ext.smallSpacer
import com.kapirti.baret.common.ext.textButton
import com.kapirti.baret.core.constants.Constants.ADS_LOG_IN_BANNER_ID
import com.kapirti.baret.tokenStringDS
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import com.kapirti.baret.R.string as AppText

@Composable
fun LogInScreen(
    restartApp: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: LogInViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val emailError = stringResource(id = AppText.email_error)
    val emptyPasswordError = stringResource(id = AppText.empty_password_error)
    val recoveryEmailSent = stringResource(id = AppText.recovery_email_sent)

    val context = LocalContext.current
    val tokenString = stringPreferencesKey("token_string")

    val tokenStringLast = flow<String> {
        context.tokenStringDS.data.map {
            it[tokenString]
        }.collect(collector = {
            if(it != null) {
                this.emit(it)
            }
        })
    }.collectAsState(initial = "false")

    AdsBannerToolbar(ADS_LOG_IN_BANNER_ID)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(text = AppText.welcome_back)

        Spacer(modifier = Modifier.smallSpacer())

        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton(AppText.log_in, Modifier.basicButton(), uiState.button) {
            viewModel.onLogInClick(
                token = tokenStringLast.value,
                restartApp = restartApp,
                onShowSnackbar = onShowSnackbar,
                emailError = emailError,
                emptyPasswordError = emptyPasswordError)
        }

        BasicTextButton(AppText.forgotten_password, Modifier.textButton()) {
            viewModel.onForgotPasswordClick(onShowSnackbar = onShowSnackbar, emailError = emailError, recoveryEmailSent = recoveryEmailSent)
        }
    }
}