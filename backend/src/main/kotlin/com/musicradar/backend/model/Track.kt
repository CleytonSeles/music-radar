package com.musicradar.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tracks")
data class Track(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    var title: String,
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    var album: Album? = null,
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", nullable = false)
    var artist: Artist,
    
    @Column
    var durationInSeconds: Int? = null,
    
    @Column
    var trackNumber: Int? = null,
    
    @Column
    var genre: String? = null,
    
    @Column
    var spotifyUrl: String? = null,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
