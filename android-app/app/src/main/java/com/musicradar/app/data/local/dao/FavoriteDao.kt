package com.musicradar.app.data.local.dao

import androidx.room.*
import com.musicradar.app.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE type = :type")
    fun getFavoritesByType(type: String): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE itemId = :itemId AND type = :type")
    suspend fun getFavorite(itemId: Long, type: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE itemId = :itemId AND type = :type")
    suspend fun deleteFavoriteById(itemId: Long, type: String)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE itemId = :itemId AND type = :type)")
    suspend fun isFavorite(itemId: Long, type: String): Boolean
}
