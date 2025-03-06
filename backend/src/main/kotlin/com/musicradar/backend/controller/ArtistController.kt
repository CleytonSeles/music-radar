package com.musicradar.backend.controller

import com.musicradar.backend.model.Artist
import com.musicradar.backend.model.dto.ArtistDTO
import com.musicradar.backend.service.ArtistService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/artists")
class ArtistController(private val artistService: ArtistService) {
    
    @GetMapping
    fun getAllArtists(): ResponseEntity<List<Artist>> {
        return ResponseEntity.ok(artistService.getAllArtists())
    }
    
    @GetMapping("/{id}")
    fun getArtistById(@PathVariable id: Long): ResponseEntity<Artist> {
        return ResponseEntity.ok(artistService.getArtistById(id))
    }
    
    @GetMapping("/search")
    fun searchArtists(@RequestParam name: String): ResponseEntity<List<Artist>> {
        return ResponseEntity.ok(artistService.searchArtistsByName(name))
    }
    
    @PostMapping
    fun createArtist(@RequestBody artistDTO: ArtistDTO): ResponseEntity<Artist> {
        val artist = Artist(
            name = artistDTO.name,
            bio = artistDTO.bio,
            imageUrl = artistDTO.imageUrl,
            genres = artistDTO.genres,
            popularity = artistDTO.popularity
        )
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(artistService.createArtist(artist))
    }
    
    @PutMapping("/{id}")
    fun updateArtist(
        @PathVariable id: Long,
        @RequestBody artistDTO: ArtistDTO
    ): ResponseEntity<Artist> {
        val artist = Artist(
            id = artistDTO.id,
            name = artistDTO.name,
            bio = artistDTO.bio,
            imageUrl = artistDTO.imageUrl,
            genres = artistDTO.genres,
            popularity = artistDTO.popularity
        )
        
        return ResponseEntity.ok(artistService.updateArtist(id, artist))
    }
    
    @DeleteMapping("/{id}")
    fun deleteArtist(@PathVariable id: Long): ResponseEntity<Void> {
        artistService.deleteArtist(id)
        return ResponseEntity.noContent().build()
    }
}
