package com.musicradar.backend.controller

import com.musicradar.backend.model.Track
import com.musicradar.backend.model.dto.TrackDTO
import com.musicradar.backend.repository.AlbumRepository
import com.musicradar.backend.repository.ArtistRepository
import com.musicradar.backend.service.TrackService
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tracks")
class TrackController(
    private val trackService: TrackService,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository
) {
    
    @GetMapping
    fun getAllTracks(): ResponseEntity<List<Track>> {
        return ResponseEntity.ok(trackService.getAllTracks())
    }
    
    @GetMapping("/{id}")
    fun getTrackById(@PathVariable id: Long): ResponseEntity<Track> {
        return ResponseEntity.ok(trackService.getTrackById(id))
    }
    
    @GetMapping("/search")
    fun searchTracks(@RequestParam title: String): ResponseEntity<List<Track>> {
        return ResponseEntity.ok(trackService.searchTracksByTitle(title))
    }
    
    @GetMapping("/artist/{artistId}")
    fun getTracksByArtistId(@PathVariable artistId: Long): ResponseEntity<List<Track>> {
        return ResponseEntity.ok(trackService.getTracksByArtistId(artistId))
    }
    
    @GetMapping("/album/{albumId}")
    fun getTracksByAlbumId(@PathVariable albumId: Long): ResponseEntity<List<Track>> {
        return ResponseEntity.ok(trackService.getTracksByAlbumId(albumId))
    }
    
    @PostMapping
    fun createTrack(@RequestBody trackDTO: TrackDTO): ResponseEntity<Track> {
        val artist = artistRepository.findById(trackDTO.artistId)
            .orElseThrow { EntityNotFoundException("Artist not found with id: ${trackDTO.artistId}") }
        
        val album = trackDTO.albumId?.let {
            albumRepository.findById(it)
                .orElseThrow { EntityNotFoundException("Album not found with id: $it") }
        }
        
        val track = Track(
            title = trackDTO.title,
            artist = artist,
            album = album,
            durationInSeconds = trackDTO.durationInSeconds,
            trackNumber = trackDTO.trackNumber,
            genre = trackDTO.genre,
            spotifyUrl = trackDTO.spotifyUrl
        )
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(trackService.createTrack(track))
    }
    
    @PutMapping("/{id}")
    fun updateTrack(
        @PathVariable id: Long,
        @RequestBody trackDTO: TrackDTO
    ): ResponseEntity<Track> {
        val artist = artistRepository.findById(trackDTO.artistId)
            .orElseThrow { EntityNotFoundException("Artist not found with id: ${trackDTO.artistId}") }
        
        val album = trackDTO.albumId?.let {
            albumRepository.findById(it)
                .orElseThrow { EntityNotFoundException("Album not found with id: $it") }
        }
        
        val track = Track(
            id = trackDTO.id,
            title = trackDTO.title,
            artist = artist,
            album = album,
            durationInSeconds = trackDTO.durationInSeconds,
            trackNumber = trackDTO.trackNumber,
            genre = trackDTO.genre,
            spotifyUrl = trackDTO.spotifyUrl
        )
        
        return ResponseEntity.ok(trackService.updateTrack(id, track))
    }
    
    @DeleteMapping("/{id}")
    fun deleteTrack(@PathVariable id: Long): ResponseEntity<Void> {
        trackService.deleteTrack(id)
        return ResponseEntity.noContent().build()
    }
}
