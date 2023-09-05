package com.kapirti.baret.core.room.recent

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_table")
data class Recent(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "display_name")
    val displayName: String = "",
    @ColumnInfo(name = "photo")
    val photo: String = ""
)