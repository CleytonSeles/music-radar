package com.musicradar.backend.repository

import com.musicradar.backend.model.Album
import com.musicradar.backend.model.Artist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AlbumRepository : JpaRepository<Album, Long> {
    fun findByArtist(artist: Artist): List<Album>
    fun findByTitleContainingIgnoreCase(title: String): List<Album>
}
