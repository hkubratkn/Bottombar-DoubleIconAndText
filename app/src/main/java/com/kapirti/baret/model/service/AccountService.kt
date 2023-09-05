package com.kapirti.baret.model.service

import com.kapirti.baret.model.UserUid
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUser: Flow<UserUid>
}