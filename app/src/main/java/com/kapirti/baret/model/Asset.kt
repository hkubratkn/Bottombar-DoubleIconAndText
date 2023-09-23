package com.kapirti.baret.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

data class Asset(
    @DocumentId val uid: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val photo: String = "",
    @ServerTimestamp val date: Timestamp? = null
)

data class AssetProfile(
    @DocumentId val uid: String = "",
    val type: String = "",
    val city: Int = 34,
    val rentSell: String = ""
)