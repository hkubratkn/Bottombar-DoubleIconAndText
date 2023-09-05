package com.kapirti.baret.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.kapirti.baret.R.string as AppText

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    FOR_YOU(
        selectedIcon = Icons.Default.Home,
        unselectedIcon = Icons.Default.Add,
        iconTextId = AppText.app_name,
        titleTextId = AppText.app_name,
    ),
    BOOKMARKS(
        selectedIcon = Icons.Default.Search,
        unselectedIcon = Icons.Default.Warning,
        iconTextId = AppText.app_name,
        titleTextId = AppText.app_name,
    ),
    INTERESTS(
        selectedIcon = Icons.Default.AccountCircle,
        unselectedIcon = Icons.Default.Settings,
        iconTextId = AppText.app_name,
        titleTextId = AppText.app_name,
    ),
}


/**
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kapirti.baret.core.constants.Constants.ADD_SCREEN
import com.kapirti.baret.core.constants.Constants.BOOKMARK_SCREEN
import com.kapirti.baret.core.constants.Constants.CHATS_SCREEN
import com.kapirti.baret.core.constants.Constants.SEARCH_SCREEN
import com.kapirti.baret.core.constants.Constants.PROFILE_SCREEN
import com.kapirti.baret.core.constants.Constants.HOME_SCREEN

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector
) {
    object Home: BottomBarScreen(
        route = HOME_SCREEN,
        icon = Icons.Default.Home
    )
    object Search: BottomBarScreen(
        route = SEARCH_SCREEN,
        icon = Icons.Default.Search
    )
    /**  object Add: BottomBarScreen(
        route = ADD_SCREEN,
        icon = Icons.Default.Add
    )
    object Bookmark: BottomBarScreen(
        route = BOOKMARK_SCREEN,
        icon = Icons.Default.Bookmarks
    )
    object Chats: BottomBarScreen(
        route = CHATS_SCREEN,
        icon = Icons.Default.Chat
    )*/
    object Profile: BottomBarScreen(
        route = PROFILE_SCREEN,
        icon = Icons.Default.AccountCircle
    )
}*/