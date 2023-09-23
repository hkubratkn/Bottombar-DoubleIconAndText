package com.kapirti.baret.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.kapirti.baret.R.string as AppText

enum class BottomBarScreen(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Default.Home,
        iconTextId = AppText.home_screen,
        titleTextId = AppText.home_screen,
    ),
    SEARCH(
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Default.Search,
        iconTextId = AppText.search_screen,
        titleTextId = AppText.search_screen,
    ),
    ADD(
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Default.Add,
        iconTextId = AppText.add_screen,
        titleTextId = AppText.add_screen,
    ),
    PROFILE(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Default.AccountCircle,
        iconTextId = AppText.profile_screen,
        titleTextId = AppText.profile_screen,
    ),
}
    /**
    object Bookmark: BottomBarScreen(
        route = BOOKMARK_SCREEN,
        icon = Icons.Default.Bookmarks
    )
    object Chats: BottomBarScreen(
        route = CHATS_SCREEN,
        icon = Icons.Default.Chat
    )*/