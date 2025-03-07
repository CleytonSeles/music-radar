package com.musicradar.backend.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    
    var title: String? = null,
    
    // Use @JsonBackReference no lado "muitos" da relação muitos-para-um
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "artist_id")
    var artist: Artist? = null,
    
    // Use @JsonManagedReference no lado "um" da relação um-para-muitos
    @JsonManagedReference
    @OneToMany(mappedBy = "album", cascade = [CascadeType.ALL])
    var tracks: MutableList<Track> = mutableListOf()
) {
    // Constructor sem argumentos exigido pelo JPA
    constructor() : this(
        id = null,
        title = null,
        artist = null
    )
}
