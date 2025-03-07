package com.musicradar.backend.controller

import com.musicradar.backend.model.Album
import com.musicradar.backend.service.AlbumService
import com.musicradar.backend.service.ArtistService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// Classe DTO para receber os dados da requisição
data class AlbumRequest(
    val title: String? = null,
    val artistId: Long? = null,
    val releaseDate: String? = null,
    val description: String? = null,
    val coverImageUrl: String? = null,
    val genre: String? = null
)

@RestController
@RequestMapping("/api/albums")
class AlbumController(
    private val albumService: AlbumService,
    private val artistService: ArtistService
) {

    @GetMapping
    fun getAllAlbums(): ResponseEntity<List<Album>> {
        val albums = albumService.getAllAlbums()
        return ResponseEntity(albums, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getAlbumById(@PathVariable id: Long): ResponseEntity<Album> {
        val album = albumService.getAlbumById(id)
        return if (album != null) {
            ResponseEntity(album, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createAlbum(@RequestBody albumRequest: AlbumRequest): ResponseEntity<Album> {
        // Criar um objeto Album com os dados da requisição
        val album = Album(
            title = albumRequest.title,
            releaseDate = albumRequest.releaseDate,
            description = albumRequest.description,
            coverImageUrl = albumRequest.coverImageUrl,
            genre = albumRequest.genre
        )
        
        // Associar ao artista se o ID for fornecido
        if (albumRequest.artistId != null) {
            val artist = artistService.getArtistById(albumRequest.artistId)
            if (artist != null) {
                album.artist = artist
            }
        }
        
        // Chamar o serviço com o objeto Album montado
        val savedAlbum = albumService.createAlbum(album)
        return ResponseEntity(savedAlbum, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateAlbum(
        @PathVariable id: Long,
        @RequestBody albumRequest: AlbumRequest
    ): ResponseEntity<Album> {
        // Obter o album existente ou retornar 404
        val existingAlbum = albumService.getAlbumById(id)
        if (existingAlbum == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        
        // Atualizar os campos do album existente
        albumRequest.title?.let { existingAlbum.title = it }
        albumRequest.releaseDate?.let { existingAlbum.releaseDate = it }
        albumRequest.description?.let { existingAlbum.description = it }
        albumRequest.coverImageUrl?.let { existingAlbum.coverImageUrl = it }
        albumRequest.genre?.let { existingAlbum.genre = it }
        
        // Atualizar o artista se o ID for fornecido
        if (albumRequest.artistId != null) {
            val artist = artistService.getArtistById(albumRequest.artistId)
            if (artist != null) {
                existingAlbum.artist = artist
            }
        }
        
        // Chamar o serviço com o objeto Album atualizado
        val updatedAlbum = albumService.updateAlbum(id, existingAlbum)
        return ResponseEntity(updatedAlbum, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteAlbum(@PathVariable id: Long): ResponseEntity<Void> {
        // Corrigido: Verifica se o album existe antes de tentar excluí-lo
        val albumExists = albumService.getAlbumById(id) != null
        
        if (albumExists) {
            albumService.deleteAlbum(id) // Não usamos o retorno diretamente
            return ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
