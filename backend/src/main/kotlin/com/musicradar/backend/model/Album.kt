package com.musicradar.backend.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "albums")
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    @Column(nullable = false)
    var title: String? = null,
    
    @Column(name = "release_date")
    var releaseDate: String? = null,
    
    @Column(columnDefinition = "TEXT")
    var description: String? = null,
    
    @Column(name = "cover_image_url")
    var coverImageUrl: String? = null,
    
    @Column
    var genre: String? = null,
    
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    
    // Use @JsonBackReference no lado "muitos" da relação
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    var artist: Artist? = null,
    
    // Use @JsonManagedReference no lado "um" da relação
    @JsonManagedReference
    @OneToMany(mappedBy = "album", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var tracks: MutableList<Track> = mutableListOf()
) {
    // Constructor sem argumentos exigido pelo JPA
    constructor() : this(
        id = null,
        title = null,
        releaseDate = null,
        description = null,
        coverImageUrl = null,
        genre = null,
        artist = null
    )
    
    // Método para atualizar o timestamp
    @PreUpdate
    fun onUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
