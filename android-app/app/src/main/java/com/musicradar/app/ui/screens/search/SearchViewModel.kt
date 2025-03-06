package com.musicradar.app.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicradar.app.data.model.Album
import com.musicradar.app.data.model.Artist
import com.musicradar.app.data.model.Track
import com.musicradar.app.data.repository.AlbumRepository
import com.musicradar.app.data.repository.ArtistRepository
import com.musicradar.app.data.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val trackRepository: TrackRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun search(query: String) {
        if (query.isEmpty()) {
            _uiState.update {
                it.copy(
                    artists = emptyList(),
                    albums = emptyList(),
                    tracks = emptyList(),
                    isLoading = false,
                    error = null
                )
            }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val artistsResult = artistRepository.searchArtistsByName(query)
                val albumsResult = albumRepository.searchAlbumsByTitle(query)
                val tracksResult = trackRepository.searchTracksByTitle(query)

                val artists = artistsResult.getOrDefault(emptyList())
                val albums = albumsResult.getOrDefault(emptyList())
                val tracks = tracksResult.getOrDefault(emptyList())

                _uiState.update {
                    it.copy(
                        artists = artists,
                        albums = albums,
                        tracks = tracks,
                        isLoading = false,
                        error = if (artists.isEmpty() && albums.isEmpty() && tracks.isEmpty())
                            "No results found for '$query'" else null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Search failed: ${e.message}",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun clearSearch() {
        _uiState.update {
            it.copy(
                artists = emptyList(),
                albums = emptyList(),
                tracks = emptyList(),
                isLoading = false,
                error = null
            )
        }
    }
}

data class SearchUiState(
    val query: String = "",
    val artists: List<Artist> = emptyList(),
    val albums: List<Album> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
