package com.kapirti.baret.ui.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import com.kapirti.baret.common.ext.card
import com.kapirti.baret.common.ext.spacer
import com.kapirti.baret.R.string as AppText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kapirti.baret.common.composable.*
import com.kapirti.baret.common.ext.fieldModifier
import com.kapirti.baret.core.constants.Constants.ADS_SETTINGS_BANNER_ID
import com.kapirti.baret.core.room.profile.Profile

@Composable
fun SettingsScreen(
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val profile = viewModel.profile.collectAsStateWithLifecycle(initialValue = Profile())
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val deleteText = stringResource(AppText.empty_password_error)

    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdsBannerToolbar(ADS_SETTINGS_BANNER_ID)
        Spacer(modifier = Modifier.spacer())

        RegularCardEditor(AppText.rate, Icons.Default.StarRate, "", Modifier.card()) {
            viewModel.rate()
        }

        RegularCardEditor(AppText.share, Icons.Default.Share, "", Modifier.card()) {
            viewModel.share()
        }

        RegularCardEditor(AppText.feedback, Icons.Default.Feedback, "", Modifier.card()) {
            viewModel.onFeedbackClick(openScreen = openScreen)
        }
        Spacer(modifier = Modifier.spacer())

        if (viewModel.uiStateAuth){
            RegularCardEditor(AppText.sign_out, Icons.Default.Logout, "", Modifier.card()) {
                showSignOutDialog = true
            }
            DangerousCardEditor(AppText.delete_my_account, Icons.Default.Delete, "", Modifier.card()) {
                showDeleteDialog = true
            }
        }
    }

    if (showSignOutDialog) {
        AlertDialog(
            title = { Text(stringResource(AppText.sign_out_title)) },
            text = { Text(stringResource(AppText.sign_out_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showSignOutDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.sign_out) {
                    viewModel.onSignOutClick(profile = profile.value, restartApp = restartApp)
                    showSignOutDialog = false
                }
            },
            onDismissRequest = { showSignOutDialog = false }
        )
    }
    if (showDeleteDialog) {
        AlertDialog(
            title = {
                Column(){
                    PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())
                    Text(stringResource(AppText.delete_account_title))
                } },
            text = { Text(stringResource(AppText.delete_account_description)) },
            dismissButton = { DialogCancelButton(AppText.cancel) { showDeleteDialog = false } },
            confirmButton = {
                DialogConfirmButton(AppText.delete_my_account) {
                    viewModel.onDeleteMyAccountClick(profile = profile.value, restartApp = restartApp, text = deleteText, onShowSnackbar = onShowSnackbar)
                    showDeleteDialog = false
                }
            },
            onDismissRequest = { showDeleteDialog = false }
        )
    }
}