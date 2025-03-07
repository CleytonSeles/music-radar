package com.musicradar.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.musicradar.app.data.local.dao.FavoriteDao
import com.musicradar.app.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
