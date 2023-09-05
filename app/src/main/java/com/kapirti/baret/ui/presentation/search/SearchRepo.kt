package com.kapirti.baret.ui.presentation.search

import com.kapirti.baret.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

object SearchRepo {
    suspend fun search(query: String, users: List<User>): List<User> = withContext(Dispatchers.Default) {
        delay(200L)
        users.filter { it.displayName.contains(query, ignoreCase = true) }
    }
}