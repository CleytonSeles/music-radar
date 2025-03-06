package com.musicradar.app.data.repository

import com.musicradar.app.data.model.Artist
import com.musicradar.app.data.remote.ArtistApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistRepository @Inject constructor(
    private val artistApi: ArtistApi
) {
    suspend fun getAllArtists(): Result<List<Artist>> {
        return try {
            val response = artistApi.getAllArtists()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch artists: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getArtistById(id: Long): Result<Artist> {
        return try {
            val response = artistApi.getArtistById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Artist not found"))
            } else {
                Result.failure(Exception("Failed to fetch artist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchArtistsByName(name: String): Result<List<Artist>> {
        return try {
            val response = artistApi.searchArtistsByName(name)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to search artists: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createArtist(artist: Artist): Result<Artist> {
        return try {
            val response = artistApi.createArtist(artist)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to create artist"))
            } else {
                Result.failure(Exception("Failed to create artist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateArtist(id: Long, artist: Artist): Result<Artist> {
        return try {
            val response = artistApi.updateArtist(id, artist)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to update artist"))
            } else {
                Result.failure(Exception("Failed to update artist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteArtist(id: Long): Result<Boolean> {
        return try {
            val response = artistApi.deleteArtist(id)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to delete artist: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
