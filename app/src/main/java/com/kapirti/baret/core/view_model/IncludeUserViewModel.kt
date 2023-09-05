package com.kapirti.baret.core.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kapirti.baret.model.User

class IncludeUserViewModel: ViewModel() {
    var user by mutableStateOf<User?>(null)
        private set

    fun addUser(newUser: User) {
        user = newUser
    }
}