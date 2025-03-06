package com.musicradar.app.data.model

import java.time.LocalDateTime

data class Artist(
    val id: Long? = null,
    val name: String,
    val bio: String? = null,
    val imageUrl: String? = null,
    val genres: String? = null,
    val popularity: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
