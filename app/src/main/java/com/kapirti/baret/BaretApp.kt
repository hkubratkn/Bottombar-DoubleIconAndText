package com.kapirti.baret
/**
import com.kapirti.baret.core.data.NetworkMonitor
import com.kapirti.baret.core.view_model.IncludeUserViewModel
import android.content.res.Resources
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.*
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
import com.kapirti.baret.common.snackbar.SnackbarManager
import com.kapirti.baret.core.constants.Constants.ADD_SCREEN
import com.kapirti.baret.core.constants.Constants.BOOKMARK_SCREEN
import com.kapirti.baret.core.constants.Constants.CHATS_SCREEN
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
import com.kapirti.baret.ui.navigation.BottomBar
import com.kapirti.baret.ui.presentation.home.HomeScreen
import com.kapirti.baret.ui.presentation.profile.ProfileScreen
import com.kapirti.baret.ui.presentation.search.SearchScreen
import com.kapirti.baret.ui.presentation.user_profile.UserProfileScreen
import com.kapirti.baret.ui.theme.BaretTheme
import com.kapirti.baret.R.string as AppText
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaretApp(networkMonitor: NetworkMonitor) {
    val includeUserViewModel: IncludeUserViewModel = viewModel()
    BaretTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState(networkMonitor = networkMonitor)
            var showBottomBar by rememberSaveable { mutableStateOf(true) }
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val isOffline by appState.isOffline.collectAsStateWithLifecycle()

            LaunchedEffect(isOffline) {
                if (isOffline) {
                    SnackbarManager.showMessage(AppText.not_connected)
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
                bottomBar = {
                    if (showBottomBar) {
                        BottomBar(navController = appState.navController)
                    }
                },
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    baretGraph(appState, includeUserViewModel)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    networkMonitor: NetworkMonitor,
) =
    remember(navController, snackbarManager, resources, coroutineScope, networkMonitor) {
        BaretAppState(navController, snackbarManager, resources, coroutineScope, networkMonitor)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

fun NavGraphBuilder.baretGraph(appState: BaretAppState, includeUserViewModel: IncludeUserViewModel) {
//    composable(SPLASH_SCREEN) { SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }) }
  //  composable(WELCOME_SCREEN) { WelcomeScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }) }
    //composable(LOG_IN_SCREEN) { LogInScreen(restartApp = { route -> appState.clearAndNavigate(route) }) }
    //composable(REGISTER_SCREEN) { RegisterScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }) }
    //composable(EDIT_SCREEN) { EditScreen(restartApp = { route -> appState.clearAndNavigate(route) }, popUpScreen = { appState.popUp() }) }
    //composable(SETTINGS_SCREEN) { SettingsScreen(restartApp = { route -> appState.clearAndNavigate(route) }, openScreen = { route -> appState.navigate(route)}) }
    composable(USER_PROFILE_SCREEN){ UserProfileScreen(includeUserViewModel = includeUserViewModel)}

    composable(HOME_SCREEN) { HomeScreen() }
    composable(SEARCH_SCREEN) { SearchScreen(openScreen = { route -> appState.navigate(route)}, includeUserViewModel = includeUserViewModel) }
   // composable(ADD_SCREEN) { AddScreen(openScreen = { route -> appState.navigate(route)}) }
   // composable(CHATS_SCREEN) { ChatsScreen(openScreen = { route -> appState.navigate(route)}) }
    //composable(BOOKMARK_SCREEN) { BookmarkScreen(openScreen = { route -> appState.navigate(route)}) }
    composable(PROFILE_SCREEN) { ProfileScreen() }
}*/

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.kapirti.baret.core.data.NetworkMonitor
import com.kapirti.baret.ui.navigation.NiaNavHost
import com.kapirti.baret.ui.navigation.TopLevelDestination
import com.kapirti.baret.ui.presentation.home.NiaNavigationBar
import com.kapirti.baret.ui.presentation.home.NiaNavigationBarItem
import com.kapirti.baret.ui.presentation.home.NiaNavigationRail
import com.kapirti.baret.ui.presentation.home.NiaNavigationRailItem

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
fun NiaApp(
    networkMonitor: NetworkMonitor,
    appState: NiaAppState = rememberNiaAppState(
        networkMonitor = networkMonitor,
    ),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    // If user is not connected to the internet show a snack bar to inform them.
    val notConnectedMessage = stringResource(R.string.not_connected)
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        }
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
            NiaBottomBar(
                destinations = appState.topLevelDestinations,
//                destinationsWithUnreadResources = unreadDestinations,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination,
                modifier = Modifier.testTag("NiaBottomBar"),
            )
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

                NiaNavHost(appState = appState, onShowSnackbar = { message, action ->
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = action,
                        duration = Short,
                    ) == ActionPerformed
                })
            }

            // TODO: We may want to add padding or spacer when the snackbar is shown so that
            //  content doesn't display behind it.
        }
    }
}

@Composable
private fun NiaNavRail(
    destinations: List<TopLevelDestination>,
//    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NiaNavigationRail(modifier = modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
//            val hasUnread = destinationsWithUnreadResources.contains(destination)
            NiaNavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun NiaBottomBar(
    destinations: List<TopLevelDestination>,
//    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NiaNavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
//            val hasUnread = destinationsWithUnreadResources.contains(destination)
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NiaNavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = destination.unselectedIcon,
                        contentDescription = null,
                    )
                },
                selectedIcon = {
                    Icon(
                        imageVector = destination.selectedIcon,
                        contentDescription = null,
                    )
                },
                label = { Text(stringResource(destination.iconTextId)) },
                modifier = Modifier,
            )
        }
    }
}

private fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false