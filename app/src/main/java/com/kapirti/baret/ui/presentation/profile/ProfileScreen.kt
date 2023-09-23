package com.kapirti.baret.ui.presentation.profile

import androidx.compose.runtime.Composable
import com.kapirti.baret.common.composable.FabAnimation
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kapirti.baret.common.composable.AdsBannerToolbar
import com.kapirti.baret.core.constants.Constants.ADS_PROFILE_BANNER_ID
import com.kapirti.baret.core.constants.ProfileState.PROFILE_AUTH
import com.kapirti.baret.core.constants.ProfileState.PROFILE_DONE
import com.kapirti.baret.core.constants.ProfileState.PROFILE_PROFILE
import com.kapirti.baret.ui.presentation.profile.type.Authantication
import com.kapirti.baret.ui.presentation.profile.type.Done
import com.kapirti.baret.ui.presentation.profile.type.Profile
import com.kapirti.baret.R.string as AppText

@Composable
internal fun ProfileRoute(openScreen: (String) -> Unit, onShowSnackbar: suspend (String, String?) -> Boolean,) {
    ProfileScreen(openScreen = openScreen, onShowSnackbar = onShowSnackbar) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    openScreen: (String) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    nestedScrollInteropConnection: NestedScrollConnection = rememberNestedScrollInteropConnection(),
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(true){ viewModel.initialize(onShowSnackbar = onShowSnackbar)}

    val profile = viewModel.profile.collectAsState(initial = null)
    val displayName = viewModel.displayName

    BoxWithConstraints(modifier = modifier
        .fillMaxSize()
        .nestedScroll(nestedScrollInteropConnection)
        .systemBarsPadding()) {
        Scaffold(
            topBar = { AdsBannerToolbar(ads = ADS_PROFILE_BANNER_ID)}
        ) {innerPadding ->
            val modifier = Modifier.padding(innerPadding)

            when(viewModel.profileType){
                PROFILE_AUTH -> Authantication(
                    onLogInClick = { viewModel.onLogInClick(openScreen)},
                    onCreateClick = { viewModel.onCreateClick(openScreen)},
                    modifier = modifier
                )
                PROFILE_PROFILE -> Profile(
                    onClick = { viewModel.onProfileClick(openScreen = openScreen) },
                    modifier = modifier)
                PROFILE_DONE -> Done(
                    profile = profile.value,
                    displayName = displayName,
                    containerHeight = this@BoxWithConstraints.maxHeight,
                    onPhotoClick = { viewModel.onPhotoClick(openScreen = openScreen) },
                    onDisplayNameClick = { viewModel.onDisplayNameClick(openScreen = openScreen) },
                    onNameSurnameClick = { viewModel.onNameSurnameClick(openScreen = openScreen) },
                    onContactClicked = { viewModel.onContactDescriptionClick(openScreen = openScreen) },
                    onDescriptionClicked = { viewModel.onContactDescriptionClick(openScreen = openScreen) },
                    modifier = modifier)

                else -> {}
            }
        }

        val fabExtended by remember { derivedStateOf { scrollState.value == 0 } }
        FabAnimation(
            text = AppText.settings,
            icon = Icons.Default.Settings,
            extended = fabExtended,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = ((-100).dp)),
            onFabClicked = {
                viewModel.onSettingsClick(openScreen)
            }
        )
    }
}