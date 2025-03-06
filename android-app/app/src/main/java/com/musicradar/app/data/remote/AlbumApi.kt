package com.musicradar.app.data.remote

import com.musicradar.app.data.model.Album
import retrofit2.Response
import retrofit2.http.*

interface AlbumApi {
    @GET("albums")
    suspend fun getAllAlbums(): Response<List<Album>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Long): Response<Album>

    @GET("albums/search")
    suspend fun searchAlbumsByTitle(@Query("title") title: String): Response<List<Album>>

    @GET("albums/artist/{artistId}")
    suspend fun getAlbumsByArtistId(@Path("artistId") artistId: Long): Response<List<Album>>

    @POST("albums")
    suspend fun createAlbum(@Body album: Album): Response<Album>

    @PUT("albums/{id}")
    suspend fun updateAlbum(@Path("id") id: Long, @Body album: Album): Response<Album>

    @DELETE("albums/{id}")
    suspend fun deleteAlbum(@Path("id") id: Long): Response<Void>
}
