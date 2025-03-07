package com.musicradar.backend.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "artists")
class Artist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @Column(nullable = false)
    var name: String? = null,
    
    @Column(columnDefinition = "TEXT")
    var bio: String? = null,
    
    @Column(name = "image_url")
    var imageUrl: String? = null,
    
    @Column
    var genres: String? = null,
    
    @Column
    var popularity: Int? = null,
    
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    // Use @JsonManagedReference no lado "um" da relação
    @JsonManagedReference
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var albums: MutableList<Album> = mutableListOf(),
    
    @JsonManagedReference
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var tracks: MutableList<Track> = mutableListOf()
) {
    // Constructor sem argumentos exigido pelo JPA
    constructor() : this(
        id = null,
        name = null,
        bio = null,
        imageUrl = null,
        genres = null,
        popularity = null
    )
    
    // Método para atualizar o timestamp
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
