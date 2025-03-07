package com.musicradar.app.data.repository

import com.musicradar.app.data.model.Album
import com.musicradar.app.data.remote.AlbumApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlbumRepository @Inject constructor(
    private val albumApi: AlbumApi
) {
    suspend fun getAllAlbums(): Result<List<Album>> {
        return try {
            val response = albumApi.getAllAlbums()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch albums: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAlbumById(id: Long): Result<Album> {
        return try {
            val response = albumApi.getAlbumById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Album not found"))
            } else {
                Result.failure(Exception("Failed to fetch album: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchAlbumsByTitle(title: String): Result<List<Album>> {
        return try {
            val response = albumApi.searchAlbumsByTitle(title)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to search albums: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAlbumsByArtistId(artistId: Long): Result<List<Album>> {
        return try {
            val response = albumApi.getAlbumsByArtistId(artistId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch artist albums: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createAlbum(album: Album): Result<Album> {
        return try {
            val response = albumApi.createAlbum(album)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to create album"))
            } else {
                Result.failure(Exception("Failed to create album: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateAlbum(id: Long, album: Album): Result<Album> {
        return try {
            val response = albumApi.updateAlbum(id, album)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to update album"))
            } else {
                Result.failure(Exception("Failed to update album: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteAlbum(id: Long): Result<Boolean> {
        return try {
            val response = albumApi.deleteAlbum(id)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to delete album: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
