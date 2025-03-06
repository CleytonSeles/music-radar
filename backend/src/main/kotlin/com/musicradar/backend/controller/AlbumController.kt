package com.musicradar.backend.controller

import com.musicradar.backend.model.Album
import com.musicradar.backend.model.dto.AlbumDTO
import com.musicradar.backend.repository.ArtistRepository
import com.musicradar.backend.service.AlbumService
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/albums")
class AlbumController(
    private val albumService: AlbumService,
    private val artistRepository: ArtistRepository
) {
    
    @GetMapping
    fun getAllAlbums(): ResponseEntity<List<Album>> {
        return ResponseEntity.ok(albumService.getAllAlbums())
    }
    
    @GetMapping("/{id}")
    fun getAlbumById(@PathVariable id: Long): ResponseEntity<Album> {
        return ResponseEntity.ok(albumService.getAlbumById(id))
    }
    
    @GetMapping("/search")
    fun searchAlbums(@RequestParam title: String): ResponseEntity<List<Album>> {
        return ResponseEntity.ok(albumService.searchAlbumsByTitle(title))
    }
    
    @GetMapping("/artist/{artistId}")
    fun getAlbumsByArtistId(@PathVariable artistId: Long): ResponseEntity<List<Album>> {
        return ResponseEntity.ok(albumService.getAlbumsByArtistId(artistId))
    }
    
    @PostMapping
    fun createAlbum(@RequestBody albumDTO: AlbumDTO): ResponseEntity<Album> {
        val artist = artistRepository.findById(albumDTO.artistId)
            .orElseThrow { EntityNotFoundException("Artist not found with id: ${albumDTO.artistId}") }
        
        val album = Album(
            title = albumDTO.title,
            artist = artist,
            releaseDate = albumDTO.releaseDate,
            description = albumDTO.description,
            coverImageUrl = albumDTO.coverImageUrl,
            genre = albumDTO.genre
        )
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(albumService.createAlbum(album))
    }
    
    @PutMapping("/{id}")
    fun updateAlbum(
        @PathVariable id: Long,
        @RequestBody albumDTO: AlbumDTO
    ): ResponseEntity<Album> {
        val artist = artistRepository.findById(albumDTO.artistId)
            .orElseThrow { EntityNotFoundException("Artist not found with id: ${albumDTO.artistId}") }
        
        val album = Album(
            id = albumDTO.id,
            title = albumDTO.title,
            artist = artist,
            releaseDate = albumDTO.releaseDate,
            description = albumDTO.description,
            coverImageUrl = albumDTO.coverImageUrl,
            genre = albumDTO.genre
        )
        
        return ResponseEntity.ok(albumService.updateAlbum(id, album))
    }
    
    @DeleteMapping("/{id}")
    fun deleteAlbum(@PathVariable id: Long): ResponseEntity<Void> {
        albumService.deleteAlbum(id)
        return ResponseEntity.noContent().build()
    }
}
