package com.kapirti.baret.ui.presentation.search

import com.kapirti.baret.core.constants.Constants.USER_PROFILE_SCREEN
import com.kapirti.baret.core.room.recent.Recent
import com.kapirti.baret.core.room.recent.RecentDao
import com.kapirti.baret.model.User
import com.kapirti.baret.model.service.FirestoreService
import com.kapirti.baret.model.service.LogService
import com.kapirti.baret.ui.presentation.BaretViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestoreService: FirestoreService,
    private val recentDao: RecentDao,
    logService: LogService
): BaretViewModel(logService){
    val users = firestoreService.usersAll
    val recents = recentDao.recents()

    fun onSearchClick(user: User, openScreen: (String) -> Unit){
        launchCatching {
            recentDao.insert(Recent(displayName = user.displayName, photo = user.photo))
            openScreen(USER_PROFILE_SCREEN)
        }
    }
    fun onDeleteClick(recent: Recent){
        launchCatching {
            recentDao.delete(recent)
        }
    }
}