package com.musicradar.backend.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
class Track(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    var title: String? = null,
    
    // Use @JsonBackReference no lado "muitos" das relações muitos-para-um
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "album_id")
    var album: Album? = null,
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "artist_id")
    var artist: Artist? = null
) {
    // Constructor sem argumentos exigido pelo JPA
    constructor() : this(
        id = null,
        title = null,
        album = null,
        artist = null
    )
}
