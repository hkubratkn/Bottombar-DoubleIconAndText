package com.kapirti.baret.core.room.profile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @ColumnInfo(name = "namedb")
    var namedb: String = "",
    @ColumnInfo(name = "surnamedb")
    var surnamedb: String = "",
    @ColumnInfo(name = "contactdb")
    var contactdb: String = "",
    @ColumnInfo(name = "descriptiondb")
    var descriptiondb: String = "",
    @ColumnInfo(name = "photodb")
    var photodb: String = "",
) {
    @PrimaryKey()
    var id: Int = 0
}