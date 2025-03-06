package com.musicradar.app.data.model

import java.time.LocalDate

data class Album(
    val id: Long? = null,
    val title: String,
    val artistId: Long,
    val artistName: String? = null,
    val releaseDate: String? = null,
    val description: String? = null,
    val coverImageUrl: String? = null,
    val genre: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
