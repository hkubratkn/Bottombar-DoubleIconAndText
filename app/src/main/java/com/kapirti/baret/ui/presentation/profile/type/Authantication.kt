package com.kapirti.baret.ui.presentation.profile.type

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kapirti.baret.common.composable.RegularCardEditor
import com.kapirti.baret.common.ext.card
import com.kapirti.baret.R.string as AppText

@Composable
fun Authantication(
    onLogInClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegularCardEditor(AppText.log_in, Icons.Default.Login, "", Modifier.card(), onLogInClick)
        RegularCardEditor(AppText.create_new_account, Icons.Default.PersonAdd, "", Modifier.card(), onCreateClick)
    }
}