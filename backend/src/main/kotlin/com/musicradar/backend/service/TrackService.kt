package com.musicradar.backend.service

import com.musicradar.backend.model.Track
import com.musicradar.backend.repository.AlbumRepository
import com.musicradar.backend.repository.ArtistRepository
import com.musicradar.backend.repository.TrackRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TrackService(
    private val trackRepository: TrackRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository
) {
    
    fun getAllTracks(): List<Track> {
        return trackRepository.findAll()
    }
    
    fun getTrackById(id: Long): Track {
        return trackRepository.findById(id)
            .orElseThrow { EntityNotFoundException("Track not found with id: $id") }
    }
    
    fun getTracksByArtistId(artistId: Long): List<Track> {
        val artist = artistRepository.findById(artistId)
            .orElseThrow { EntityNotFoundException("Artist not found with id: $artistId") }
        return trackRepository.findByArtist(artist)
    }
    
    fun getTracksByAlbumId(albumId: Long): List<Track> {
        val album = albumRepository.findById(albumId)
            .orElseThrow { EntityNotFoundException("Album not found with id: $albumId") }
        return trackRepository.findByAlbum(album)
    }
    
    fun searchTracksByTitle(title: String): List<Track> {
        return trackRepository.findByTitleContainingIgnoreCase(title)
    }
    
    fun createTrack(track: Track): Track {
        return trackRepository.save(track)
    }
    
    fun updateTrack(id: Long, trackDetails: Track): Track {
        val existingTrack = getTrackById(id)
        
        existingTrack.title = trackDetails.title
        existingTrack.durationInSeconds = trackDetails.durationInSeconds
        existingTrack.trackNumber = trackDetails.trackNumber
        existingTrack.genre = trackDetails.genre
        existingTrack.spotifyUrl = trackDetails.spotifyUrl
        existingTrack.updatedAt = LocalDateTime.now()
        
        return trackRepository.save(existingTrack)
    }
    
    fun deleteTrack(id: Long) {
        val track = getTrackById(id)
        trackRepository.delete(track)
    }
}
