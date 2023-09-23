package com.kapirti.baret
/**
   // composable(CHATS_SCREEN) { ChatsScreen(openScreen = { route -> appState.navigate(route)}) }
    //composable(BOOKMARK_SCREEN) { BookmarkScreen(openScreen = { route -> appState.navigate(route)}) }
}*/

import com.kapirti.baret.core.data.NetworkMonitor
import com.kapirti.baret.core.view_model.IncludeUserViewModel
import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kapirti.baret.core.constants.Constants.EDIT_SCREEN
import com.kapirti.baret.core.constants.Constants.LOG_IN_SCREEN
import com.kapirti.baret.core.constants.Constants.SEARCH_SCREEN
import com.kapirti.baret.core.constants.Constants.PROFILE_SCREEN
import com.kapirti.baret.core.constants.Constants.REGISTER_SCREEN
import com.kapirti.baret.core.constants.Constants.SETTINGS_SCREEN
import com.kapirti.baret.core.constants.Constants.SPLASH_SCREEN
import com.kapirti.baret.core.constants.Constants.WELCOME_SCREEN
import com.kapirti.baret.core.constants.Constants.HOME_SCREEN
import com.kapirti.baret.core.constants.Constants.USER_PROFILE_SCREEN
import com.kapirti.baret.ui.theme.BaretTheme
import com.kapirti.baret.R.string as AppText
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kapirti.baret.core.constants.Constants.ADD_SCREEN
import com.kapirti.baret.ui.navigation.BaretBottomBar
import com.kapirti.baret.ui.presentation.add.AddScreen
import com.kapirti.baret.ui.presentation.edit.EditScreen
import com.kapirti.baret.ui.presentation.home.HomeRoute
import com.kapirti.baret.ui.presentation.log_in.LogInScreen
import com.kapirti.baret.ui.presentation.profile.ProfileRoute
import com.kapirti.baret.ui.presentation.register.RegisterScreen
import com.kapirti.baret.ui.presentation.search.SearchRoute
import com.kapirti.baret.ui.presentation.settings.SettingsScreen
import com.kapirti.baret.ui.presentation.splash.SplashScreen
import com.kapirti.baret.ui.presentation.user_profile.UserProfileScreen
import com.kapirti.baret.ui.presentation.welcome.WelcomeScreen
import kotlinx.coroutines.CoroutineScope

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun BaretApp(
    networkMonitor: NetworkMonitor,
    appState: BaretAppState = rememberAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val includeUserViewModel: IncludeUserViewModel = viewModel()

    BaretTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            var showBottomBar by rememberSaveable { mutableStateOf(true) }
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val isOffline by appState.isOffline.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            val notConnectedMessage = stringResource(AppText.not_connected)
            LaunchedEffect(isOffline) {
                if (isOffline) {
                    snackbarHostState.showSnackbar(
                        message = notConnectedMessage,
                        duration = Indefinite,
                    )
                }
            }

            showBottomBar = when (navBackStackEntry?.destination?.route) {
                SPLASH_SCREEN -> false
                WELCOME_SCREEN -> false
                LOG_IN_SCREEN -> false
                REGISTER_SCREEN -> false
                EDIT_SCREEN -> false
                USER_PROFILE_SCREEN -> false
                SETTINGS_SCREEN -> false

                else -> true
            }


            Scaffold(
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                },
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { SnackbarHost(snackbarHostState) },
                bottomBar = {
                    if (showBottomBar) {
                        BaretBottomBar(
                            destinations = appState.bottomBarScreens,
//                destinationsWithUnreadResources = unreadDestinations,
                            onNavigateToDestination = appState::navigateToTopLevelDestination,
                            currentDestination = appState.currentDestination,
                            modifier = Modifier.testTag("NiaBottomBar"),
                        )
                    }
                },
            ) { padding ->
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    Column(Modifier.fillMaxSize()) {

                        BaretNavHost(
                            appState = appState,
                            includeUserViewModel = includeUserViewModel,
                            onShowSnackbar = { message, action ->
                            snackbarHostState.showSnackbar(
                                message = message,
                                actionLabel = action,
                                duration = Short,
                            ) == ActionPerformed
                        })

                    }
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
    resources: Resources = resources(),
): BaretAppState {
    return remember(navController, coroutineScope, networkMonitor, resources,) {
        BaretAppState(navController, coroutineScope, networkMonitor, resources,)
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
fun BaretNavHost(
    appState: BaretAppState,
    includeUserViewModel: IncludeUserViewModel,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = SPLASH_SCREEN,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        baretGraph(appState = appState, onShowSnackbar = onShowSnackbar, includeUserViewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
fun NavGraphBuilder.baretGraph(appState: BaretAppState, onShowSnackbar: suspend (String, String?) -> Boolean, includeUserViewModel: IncludeUserViewModel) {
    composable(SPLASH_SCREEN) { SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }, onShowSnackbar = onShowSnackbar) }
    composable(WELCOME_SCREEN) { WelcomeScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }, onShowSnackbar = onShowSnackbar) }
    composable(LOG_IN_SCREEN) { LogInScreen(restartApp = { route -> appState.clearAndNavigate(route) }, onShowSnackbar = onShowSnackbar) }
    composable(REGISTER_SCREEN) { RegisterScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }, onShowSnackbar = onShowSnackbar) }
    composable(EDIT_SCREEN) { EditScreen(restartApp = { route -> appState.clearAndNavigate(route) }, popUpScreen = { appState.popUp() }) }
    composable(USER_PROFILE_SCREEN){ UserProfileScreen(popUpScreen = { appState.popUp() }, openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }, includeUserViewModel = includeUserViewModel, onShowSnackbar = onShowSnackbar)}
    composable(SETTINGS_SCREEN) { SettingsScreen(restartApp = { route -> appState.clearAndNavigate(route) }, openScreen = { route -> appState.navigate(route)}, onShowSnackbar = onShowSnackbar) }

    composable(HOME_SCREEN) { HomeRoute(restartApp = { route -> appState.clearAndNavigate(route) }, openScreen = { route -> appState.navigate(route)},) }
    composable(SEARCH_SCREEN) { SearchRoute(openScreen = { route -> appState.navigate(route)}, includeUserViewModel = includeUserViewModel, onShowSnackbar = onShowSnackbar) }
    composable(ADD_SCREEN) { AddScreen(openScreen = { route -> appState.navigate(route)}, onShowSnackbar = onShowSnackbar) }
    composable(PROFILE_SCREEN) { ProfileRoute(openScreen = { route -> appState.navigate(route)}, onShowSnackbar = onShowSnackbar) }
}