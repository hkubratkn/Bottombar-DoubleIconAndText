package com.kapirti.baret.ui.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.baret.common.composable.*
import com.kapirti.baret.common.ext.basicButton
import com.kapirti.baret.common.ext.fieldModifier
import com.kapirti.baret.common.ext.smallSpacer
import com.kapirti.baret.core.constants.Constants.ADS_REGISTER_BANNER_ID
import com.kapirti.baret.R.string as AppText

@Composable
fun RegisterScreen(
    openAndPopUp: (String, String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()
    val emailError = stringResource(id = AppText.email_error)
    val passwordError = stringResource(id = AppText.password_error)
    val passwordMatchError = stringResource(id = AppText.password_match_error)

    AdsBannerToolbar(ads = ADS_REGISTER_BANNER_ID)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(text = AppText.register)

        Spacer(modifier = Modifier.smallSpacer())

        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)
        RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange, fieldModifier)

        Spacer(modifier = Modifier.smallSpacer())

        HyperlinkText(
            fullText = "By clicking Register, you are accepting the Terms of use and Privacy Policy.",
            linkText = listOf("Terms of use", "Privacy Policy"),
            hyperlink = listOf("http://kapirti.lovestoblog.com/","http://kapirti.lovestoblog.com/"),
            fontSize = MaterialTheme.typography.displaySmall.fontSize
        )

        BasicButton(AppText.register, Modifier.basicButton(), uiState.button) {
            viewModel.onRegisterClick(openAndPopUp = openAndPopUp, onShowSnackbar = onShowSnackbar,
                emailError = emailError, passwordError = passwordError, passwordMatchError = passwordMatchError)
        }
    }
}