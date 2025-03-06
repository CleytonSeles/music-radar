package com.musicradar.app.data.remote

import com.musicradar.app.data.model.Artist
import retrofit2.Response
import retrofit2.http.*

interface ArtistApi {
    @GET("artists")
    suspend fun getAllArtists(): Response<List<Artist>>

    @GET("artists/{id}")
    suspend fun getArtistById(@Path("id") id: Long): Response<Artist>

    @GET("artists/search")
    suspend fun searchArtistsByName(@Query("name") name: String): Response<List<Artist>>

    @POST("artists")
    suspend fun createArtist(@Body artist: Artist): Response<Artist>

    @PUT("artists/{id}")
    suspend fun updateArtist(@Path("id") id: Long, @Body artist: Artist): Response<Artist>

    @DELETE("artists/{id}")
    suspend fun deleteArtist(@Path("id") id: Long): Response<Void>
}
