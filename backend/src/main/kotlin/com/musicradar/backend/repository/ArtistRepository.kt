   package com.musicradar.backend.repository

   import com.musicradar.backend.model.Artist
   import org.springframework.data.jpa.repository.JpaRepository
   import org.springframework.stereotype.Repository

   @Repository
   interface ArtistRepository : JpaRepository<Artist, Long> {
       fun findByNameContainingIgnoreCase(name: String): List<Artist>
   }
