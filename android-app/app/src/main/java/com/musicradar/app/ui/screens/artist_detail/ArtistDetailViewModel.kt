package com.musicradar.app.ui.screens.artist_detail

import androidx.lifecycle.SavedStateHandle
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
class ArtistDetailViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    private val trackRepository: TrackRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val artistId: Long = checkNotNull(savedStateHandle["artistId"])

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> = _uiState.asStateFlow()

    init {
        loadArtistData()
    }

    // Novo método privado que centraliza todas as chamadas de carregamento
    private fun loadArtistData() {
        loadArtist()
        loadArtistAlbums()
        loadArtistTracks()
    }

    private fun loadArtist() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingArtist = true, artistError = null) }

            artistRepository.getArtistById(artistId)
                .onSuccess { artist ->
                    _uiState.update {
                        it.copy(
                            artist = artist,
                            isLoadingArtist = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            artistError = error.message ?: "Failed to load artist",
                            isLoadingArtist = false
                        )
                    }
                }
        }
    }

    private fun loadArtistAlbums() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingAlbums = true, albumsError = null) }

            albumRepository.getAlbumsByArtistId(artistId)
                .onSuccess { albums ->
                    _uiState.update {
                        it.copy(
                            albums = albums,
                            isLoadingAlbums = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            albumsError = error.message ?: "Failed to load albums",
                            isLoadingAlbums = false
                        )
                    }
                }
        }
    }

    private fun loadArtistTracks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingTracks = true, tracksError = null) }

            trackRepository.getTracksByArtistId(artistId)
                .onSuccess { tracks ->
                    _uiState.update {
                        it.copy(
                            tracks = tracks,
                            isLoadingTracks = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            tracksError = error.message ?: "Failed to load tracks",
                            isLoadingTracks = false
                        )
                    }
                }
        }
    }

    // Método público para refresh mantido
    fun refresh() {
        loadArtistData()
    }
}

data class ArtistDetailUiState(
    val artist: Artist? = null,
    val albums: List<Album> = emptyList(),
    val tracks: List<Track> = emptyList(),
    val isLoadingArtist: Boolean = false,
    val isLoadingAlbums: Boolean = false,
    val isLoadingTracks: Boolean = false,
    val artistError: String? = null,
    val albumsError: String? = null,
    val tracksError: String? = null
)

