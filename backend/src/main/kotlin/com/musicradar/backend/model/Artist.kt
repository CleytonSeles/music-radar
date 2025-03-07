package com.musicradar.backend.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Artist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    var name: String? = null,
    var bio: String? = null,
    var imageUrl: String? = null,
    var genres: String? = null,
    var popularity: Int? = null,
    
    // Use @JsonManagedReference no lado "um" da relação um-para-muitos
    @JsonManagedReference
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL])
    var albums: MutableList<Album> = mutableListOf(),
    
    @JsonManagedReference
    @OneToMany(mappedBy = "artist", cascade = [CascadeType.ALL])
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
}
