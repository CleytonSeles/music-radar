package com.musicradar.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemId: Long,
    val type: String, // "ARTIST", "ALBUM" ou "TRACK"
    val name: String,
    val imageUrl: String?,
    val addedAt: Long = System.currentTimeMillis()
)
