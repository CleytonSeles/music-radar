package com.musicradar.app.data.remote

import com.musicradar.app.data.model.Track
import retrofit2.Response
import retrofit2.http.*

interface TrackApi {
    @GET("tracks")
    suspend fun getAllTracks(): Response<List<Track>>

    @GET("tracks/{id}")
    suspend fun getTrackById(@Path("id") id: Long): Response<Track>

    @GET("tracks/search")
    suspend fun searchTracksByTitle(@Query("title") title: String): Response<List<Track>>

    @GET("tracks/artist/{artistId}")
    suspend fun getTracksByArtistId(@Path("artistId") artistId: Long): Response<List<Track>>

    @GET("tracks/album/{albumId}")
    suspend fun getTracksByAlbumId(@Path("albumId") albumId: Long): Response<List<Track>>

    @POST("tracks")
    suspend fun createTrack(@Body track: Track): Response<Track>

    @PUT("tracks/{id}")
    suspend fun updateTrack(@Path("id") id: Long, @Body track: Track): Response<Track>

    @DELETE("tracks/{id}")
    suspend fun deleteTrack(@Path("id") id: Long): Response<Void>
}
