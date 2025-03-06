package com.musicradar.app.data.model

data class Track(
    val id: Long? = null,
    val title: String,
    val albumId: Long?,
    val albumTitle: String? = null,
    val artistId: Long,
    val artistName: String? = null,
    val durationInSeconds: Int? = null,
    val trackNumber: Int? = null,
    val genre: String? = null,
    val spotifyUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
