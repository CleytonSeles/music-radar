package com.musicradar.backend.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "albums")
data class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    var title: String,
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id", nullable = false)
    var artist: Artist,
    
    @Column
    var releaseDate: LocalDate? = null,
    
    @Column(columnDefinition = "TEXT")
    var description: String? = null,
    
    @Column
    var coverImageUrl: String? = null,
    
    @Column
    var genre: String? = null,
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)
