package com.musicradar.backend.service

import com.musicradar.backend.model.Album
import com.musicradar.backend.repository.AlbumRepository
import com.musicradar.backend.repository.ArtistRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AlbumService(
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
) {
    
    fun getAllAlbums(): List<Album> {
        return albumRepository.findAll()
    }
    
    fun getAlbumById(id: Long): Album {
        return albumRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Album not found with id: $id") }
    }
    
    fun getAlbumsByArtistId(artistId: Long): List<Album> {
        val artist = artistRepository.findById(artistId)
            .orElseThrow { EntityNotFoundException("Artist not found with id: $artistId") }
        return albumRepository.findByArtist(artist)
    }
    
    fun searchAlbumsByTitle(title: String): List<Album> {
        return albumRepository.findByTitleContainingIgnoreCase(title)
    }
    
    fun createAlbum(album: Album): Album {
        return albumRepository.save(album)
    }
    
    fun updateAlbum(id: Long, albumDetails: Album): Album {
        val existingAlbum = getAlbumById(id)
        
        existingAlbum.title = albumDetails.title
        existingAlbum.releaseDate = albumDetails.releaseDate
        existingAlbum.description = albumDetails.description
        existingAlbum.coverImageUrl = albumDetails.coverImageUrl
        existingAlbum.genre = albumDetails.genre
        existingAlbum.updatedAt = LocalDateTime.now()
        
        return albumRepository.save(existingAlbum)
    }
    
    fun deleteAlbum(id: Long) {
        val album = getAlbumById(id)
        albumRepository.delete(album)
    }
}
