package com.kapirti.baret.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class User(
    val uid: String = "",
    val displayName: String = "",
    val name: String = "",
    val surname: String = "",
    val photo: String = "",
    val birthday: String = "",
    val contact: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val online: Boolean = false,
    val token: String = "",
    @ServerTimestamp
    var lastSeen: Timestamp? = null,
    @ServerTimestamp
    var date: Timestamp? = null
)