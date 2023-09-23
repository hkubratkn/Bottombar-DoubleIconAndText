package com.kapirti.baret.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.kapirti.baret.common.composable.NiaNavigationBar
import com.kapirti.baret.common.composable.NiaNavigationBarItem

@Composable
fun BaretBottomBar(
    destinations: List<BottomBarScreen>,
//    destinationsWithUnreadResources: Set<TopLevelDestination>,
    onNavigateToDestination: (BottomBarScreen) -> Unit,
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

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: BottomBarScreen) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false






/**
fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    this.navigate(searchRoute, navOptions)
}





//const val bookmarksRoute = "bookmarks_route"

fun NavController.navigateToBookmarks(navOptions: NavOptions? = null) {
    this.navigate(bookmarksRoute, navOptions)
}

const val LINKED_NEWS_RESOURCE_ID = "linkedNewsResourceId"
const val forYouNavigationRoute = "for_you_route/{$LINKED_NEWS_RESOURCE_ID}"
private const val DEEP_LINK_URI_PATTERN =
    "https://www.nowinandroid.apps.samples.google.com/foryou/{$LINKED_NEWS_RESOURCE_ID}"




private const val INTERESTS_GRAPH_ROUTE_PATTERN = "interests_graph"
const val interestsRoute = "interests_route"



fun NavGraphBuilder.interestsGraph() {
    navigation(
        route = INTERESTS_GRAPH_ROUTE_PATTERN,
        startDestination = interestsRoute,
    ) {
        composable(route = interestsRoute) {
            InterestsRoute()
        }
    }*/