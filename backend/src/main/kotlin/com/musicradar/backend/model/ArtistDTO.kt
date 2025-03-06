package com.musicradar.backend.model.dto

data class ArtistDTO(
    val id: Long? = null,
    val name: String,
    val bio: String? = null,
    val imageUrl: String? = null,
    val genres: String? = null,
    val popularity: Int? = null
)
