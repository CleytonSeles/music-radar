package com.musicradar.backend.repository

import com.musicradar.backend.model.Album
import com.musicradar.backend.model.Artist
import com.musicradar.backend.model.Track
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrackRepository : JpaRepository<Track, Long> {
    fun findByAlbum(album: Album): List<Track>
    fun findByArtist(artist: Artist): List<Track>
    fun findByTitleContainingIgnoreCase(title: String): List<Track>
}
