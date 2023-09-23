package com.kapirti.baret

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.kapirti.baret.core.constants.Constants.ADD_SCREEN
import com.kapirti.baret.core.constants.Constants.HOME_SCREEN
import com.kapirti.baret.core.constants.Constants.PROFILE_SCREEN
import com.kapirti.baret.core.constants.Constants.SEARCH_SCREEN
import com.kapirti.baret.ui.navigation.BottomBarScreen.ADD
import com.kapirti.baret.ui.navigation.BottomBarScreen.SEARCH
import com.kapirti.baret.ui.navigation.BottomBarScreen.HOME
import com.kapirti.baret.ui.navigation.BottomBarScreen.PROFILE
import com.kapirti.baret.core.data.NetworkMonitor
import com.kapirti.baret.ui.navigation.BottomBarScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class BaretAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor,
    private val resources: Resources,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentBottomBarScreen: BottomBarScreen?
        @Composable get() = when (currentDestination?.route) {
            HOME_SCREEN -> HOME
            SEARCH_SCREEN -> SEARCH
            ADD_SCREEN -> ADD
            PROFILE_SCREEN -> PROFILE
            else -> null
        }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val bottomBarScreens: List<BottomBarScreen> = BottomBarScreen.values().asList()

    fun navigateToTopLevelDestination(bottomBarScreen: BottomBarScreen) {
       // trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }

            when (bottomBarScreen) {
                HOME -> navController.navigateToHome(topLevelNavOptions)
                SEARCH -> navController.navigateToSearch(topLevelNavOptions)
                ADD -> navController.navigateToAdd(topLevelNavOptions)
                PROFILE -> navController.navigateToProfile(topLevelNavOptions)
            }
        }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}

private fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_SCREEN, navOptions)
}

private fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(SEARCH_SCREEN, navOptions)
}

private fun NavController.navigateToAdd(navOptions: NavOptions? = null) {
    this.navigate(ADD_SCREEN, navOptions)
}

private fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    this.navigate(PROFILE_SCREEN, navOptions)
}