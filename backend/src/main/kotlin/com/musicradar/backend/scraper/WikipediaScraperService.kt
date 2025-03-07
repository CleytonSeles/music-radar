package com.musicradar.backend.scraper

import com.musicradar.backend.model.Artist
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class WikipediaScraperService {
    private val logger = LoggerFactory.getLogger(WikipediaScraperService::class.java)

    fun searchArtistInfo(artistName: String): Map<String, String> {
        val result = mutableMapOf<String, String>()
        try {
            val encodedName = URLEncoder.encode(artistName, StandardCharsets.UTF_8.toString())
            val url = "https://en.wikipedia.org/wiki/$encodedName"
            logger.info("Scraping data from: $url")
            
            val document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.9999.99 Safari/537.36")
                .get()
                
            // Extrair bio do primeiro parágrafo
            val firstParagraph = document.select("div.mw-parser-output > p:not(.mw-empty-elt)").first()
            val bio = firstParagraph?.text()
            if (!bio.isNullOrBlank()) {
                result["bio"] = bio
            }
            
            // Tentar obter imagem
            val imageElement = document.select("table.infobox a.image img").first()
            val imageUrl = imageElement?.attr("src")
            if (!imageUrl.isNullOrBlank()) {
                result["imageUrl"] = if (imageUrl.startsWith("//")) "https:$imageUrl" else imageUrl
            }
            
            // Tentar obter gêneros
            val genreElements = document.select("table.infobox th:contains(Genres), table.infobox th:contains(Genre)").first()
            if (genreElements != null) {
                val genreTd = genreElements.parent()?.select("td")?.first()
                val genres = genreTd?.text()
                if (!genres.isNullOrBlank()) {
                    result["genres"] = genres
                }
            }
            
            logger.info("Scraped data for $artistName: $result")
        } catch (e: Exception) {
            logger.error("Error scraping Wikipedia for artist $artistName", e)
        }
        return result
    }
    
    fun enrichArtistWithScrapedData(artist: Artist): Artist {
        // Verifica se artist.name é nulo antes de continuar
        if (artist.name.isNullOrBlank()) {
            logger.warn("Cannot enrich artist with null or blank name")
            return artist
        }
        
        val scrapedData = searchArtistInfo(artist.name!!)
        
        // Corrigido: use operador de chamada segura para evitar TypeMismatch
        if (artist.bio.isNullOrBlank() && scrapedData.containsKey("bio")) {
            artist.bio = scrapedData["bio"] // String? -> String? é seguro
        }
        
        if (artist.imageUrl.isNullOrBlank() && scrapedData.containsKey("imageUrl")) {
            artist.imageUrl = scrapedData["imageUrl"] // String? -> String? é seguro
        }
        
        if (artist.genres.isNullOrBlank() && scrapedData.containsKey("genres")) {
            artist.genres = scrapedData["genres"] // String? -> String? é seguro
        }
        
        return artist
    }
}
