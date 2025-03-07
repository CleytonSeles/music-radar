package com.musicradar.app.di

import android.content.Context
import androidx.room.Room
import com.musicradar.app.data.local.MusicDatabase
import com.musicradar.app.data.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MusicDatabase {
        return Room.databaseBuilder(
            context,
            MusicDatabase::class.java,
            "music_database"
        ).build()
    }

    @Provides
    fun provideFavoriteDao(database: MusicDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}
