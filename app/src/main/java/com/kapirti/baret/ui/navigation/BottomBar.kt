package com.kapirti.baret.ui.navigation



/**
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController) {
val screens = listOf(
BottomBarScreen.Home,
BottomBarScreen.Search,
//        BottomBarScreen.Add,
//      BottomBarScreen.Bookmark,
//    BottomBarScreen.Chats,
BottomBarScreen.Profile
)
val navBackStackEntry by navController.currentBackStackEntryAsState()
val currentDestination = navBackStackEntry?.destination

BottomNavigation {
screens.forEach { screen ->
AddItem(
screen = screen,
currentDestination = currentDestination,
navController = navController
)
}
}
}

@Composable
private fun RowScope.AddItem(
screen: BottomBarScreen,
currentDestination: NavDestination?,
navController: NavHostController
) {
BottomNavigationItem(
icon = {
Icon(
imageVector = screen.icon,
contentDescription = null
)
},
selected = currentDestination?.hierarchy?.any {
it.route == screen.route
} == true,
unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
onClick = {
navController.navigate(screen.route) {
popUpTo(navController.graph.findStartDestination().id)
launchSingleTop = true
}
}
)
}*/


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.kapirti.baret.NiaAppState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation

@Composable
fun NiaNavHost(
    appState: NiaAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    startDestination: String = forYouNavigationRoute,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        forYouScreen()
        bookmarksScreen()
        searchScreen()
        interestsGraph()
    }
}

const val searchRoute = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}

fun NavGraphBuilder.searchScreen() { composable(route = searchRoute) { SearchRoute() } }

@Composable
internal fun SearchRoute(
    modifier: Modifier = Modifier,
) {
    SearchScreen(
        modifier = modifier,
    )
}

@Composable
internal fun SearchScreen(modifier: Modifier = Modifier, ) {}

const val bookmarksRoute = "bookmarks_route"

fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    this.navigate(bookmarksRoute, navOptions)
}

fun NavGraphBuilder.bookmarksScreen(
) {
    composable(route = bookmarksRoute) {
        BookmarksRoute()
    }
}

@Composable
internal fun BookmarksRoute() { BookmarksScreen() }

@Composable
internal fun BookmarksScreen() { }

const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"
const val forYouNavigationRoute = "for_you_route/{$LINKED_NEWS_RESOURCE_ID}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou/{$LINKED_NEWS_RESOURCE_ID}"

fun NavController.navigateToForYou(navOptions: NavOptions? = null) {
    this.navigate(forYouNavigationRoute, navOptions)
}

fun NavGraphBuilder.forYouScreen() {
    composable(
        route = forYouNavigationRoute,
        deepLinks = listOf(
            navDeepLink { uriPattern = DEEP_LINK_URI_PATTERN },
        ),
        arguments = listOf(
            navArgument(LINKED_NEWS_RESOURCE_ID) { type = NavType.StringType },
        ),
    ) {
        ForYouRoute()
    }
}

@Composable
internal fun ForYouRoute() { ForYouScreen() }

@Composable
internal fun ForYouScreen() {}

private const val INTERESTS_GRAPH_ROUTE_PATTERN = "interests_graph"
const val interestsRoute = "interests_route"

fun NavController.navigateToInterestsGraph(navOptions: NavOptions? = null) {
    this.navigate(INTERESTS_GRAPH_ROUTE_PATTERN, navOptions)
}

fun NavGraphBuilder.interestsGraph() {
    navigation(
        route = INTERESTS_GRAPH_ROUTE_PATTERN,
        startDestination = interestsRoute,
    ) {
        composable(route = interestsRoute) {
            InterestsRoute()
        }
    }
}


@Composable
internal fun InterestsRoute() { InterestsScreen() }

@Composable
internal fun InterestsScreen() {}