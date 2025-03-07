package com.musicradar.app.data.repository

import com.musicradar.app.data.model.Track
import com.musicradar.app.data.remote.TrackApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackRepository @Inject constructor(
    private val trackApi: TrackApi
) {
    suspend fun getAllTracks(): Result<List<Track>> {
        return try {
            val response = trackApi.getAllTracks()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch tracks: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTrackById(id: Long): Result<Track> {
        return try {
            val response = trackApi.getTrackById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Track not found"))
            } else {
                Result.failure(Exception("Failed to fetch track: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchTracksByTitle(title: String): Result<List<Track>> {
        return try {
            val response = trackApi.searchTracksByTitle(title)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to search tracks: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTracksByArtistId(artistId: Long): Result<List<Track>> {
        return try {
            val response = trackApi.getTracksByArtistId(artistId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch artist tracks: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTracksByAlbumId(albumId: Long): Result<List<Track>> {
        return try {
            val response = trackApi.getTracksByAlbumId(albumId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch album tracks: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createTrack(track: Track): Result<Track> {
        return try {
            val response = trackApi.createTrack(track)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to create track"))
            } else {
                Result.failure(Exception("Failed to create track: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTrack(id: Long, track: Track): Result<Track> {
        return try {
            val response = trackApi.updateTrack(id, track)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to update track"))
            } else {
                Result.failure(Exception("Failed to update track: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteTrack(id: Long): Result<Boolean> {
        return try {
            val response = trackApi.deleteTrack(id)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to delete track: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
