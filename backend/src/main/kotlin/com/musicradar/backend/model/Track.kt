package com.musicradar.backend.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tracks")
class Track(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @Column(nullable = false)
    var title: String? = null,
    
    @Column(name = "duration_in_seconds")
    var durationInSeconds: Int? = null,
    
    @Column(name = "track_number")
    var trackNumber: Int? = null,
    
    @Column
    var genre: String? = null,
    
    @Column(name = "spotify_url")
    var spotifyUrl: String? = null,
    
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    // Use @JsonBackReference no lado "muitos" das relações
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    var album: Album? = null,
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    var artist: Artist? = null
) {
    // Constructor sem argumentos exigido pelo JPA
    constructor() : this(
        id = null,
        title = null,
        durationInSeconds = null,
        trackNumber = null,
        genre = null,
        spotifyUrl = null,
        album = null,
        artist = null
    )
    
    // Método para atualizar o timestamp
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
