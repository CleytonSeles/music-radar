package com.musicradar.backend.service

import com.musicradar.backend.model.Artist
import com.musicradar.backend.repository.ArtistRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import jakarta.persistence.EntityNotFoundException

@Service
class ArtistService(private val artistRepository: ArtistRepository) {
    
    fun getAllArtists(): List<Artist> {
        return artistRepository.findAll()
    }
    
    fun getArtistById(id: Long): Artist {
        return artistRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Artist not found with id: $id") }
    }
    
    fun searchArtistsByName(name: String): List<Artist> {
        return artistRepository.findByNameContainingIgnoreCase(name)
    }
    
    fun createArtist(artist: Artist): Artist {
        return artistRepository.save(artist)
    }
    
    fun updateArtist(id: Long, artistDetails: Artist): Artist {
        val existingArtist = getArtistById(id)
        
        existingArtist.name = artistDetails.name
        existingArtist.bio = artistDetails.bio
        existingArtist.imageUrl = artistDetails.imageUrl
        existingArtist.genres = artistDetails.genres
        existingArtist.popularity = artistDetails.popularity
        existingArtist.updatedAt = LocalDateTime.now()
        
        return artistRepository.save(existingArtist)
    }
    
    fun deleteArtist(id: Long) {
        val artist = getArtistById(id)
        artistRepository.delete(artist)
    }
}
