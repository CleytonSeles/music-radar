package com.musicradar.backend.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "artists")
data class Artist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    var name: String,
    
    @Column(columnDefinition = "TEXT")
    var bio: String? = null,
    
    @Column
    var imageUrl: String? = null,
    
    @Column
    var genres: String? = null,
    
    @Column
    var popularity: Int? = null,
    
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL], orphanRemoval = true)
    var albums: MutableList<Album> = mutableListOf(),
    
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL], orphanRemoval = true)
    var tracks: MutableList<Track> = mutableListOf(),
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
