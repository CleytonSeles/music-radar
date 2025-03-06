package com.musicradar.backend.model.dto

import java.time.LocalDate

data class AlbumDTO(
    val id: Long? = null,
    val title: String,
    val artistId: Long,
    val releaseDate: LocalDate? = null,
    val description: String? = null,
    val coverImageUrl: String? = null,
    val genre: String? = null
)
