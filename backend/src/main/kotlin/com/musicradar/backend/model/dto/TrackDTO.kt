package com.musicradar.backend.model.dto

data class TrackDTO(
    val id: Long? = null,
    val title: String,
    val albumId: Long? = null,
    val artistId: Long,
    val durationInSeconds: Int? = null,
    val trackNumber: Int? = null,
    val genre: String? = null,
    val spotifyUrl: String? = null
)
