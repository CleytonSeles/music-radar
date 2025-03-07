package com.musicradar.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicradar.app.data.model.Album
import com.musicradar.app.data.model.Artist
import com.musicradar.app.data.repository.AlbumRepository
import com.musicradar.app.data.repository.ArtistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    // Novo método privado que centraliza todas as chamadas de carregamento
    private fun loadHomeData() {
        loadFeaturedArtists()
        loadRecentAlbums()
    }

    private fun loadFeaturedArtists() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingArtists = true) }
            artistRepository.getAllArtists()
                .onSuccess { artists ->
                    _uiState.update {
                        it.copy(
                            featuredArtists = artists.take(5),
                            isLoadingArtists = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            artistsError = error.message ?: "Failed to load artists",
                            isLoadingArtists = false
                        )
                    }
                }
        }
    }

    private fun loadRecentAlbums() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingAlbums = true) }
            albumRepository.getAllAlbums()
                .onSuccess { albums ->
                    _uiState.update {
                        it.copy(
                            recentAlbums = albums.take(5),
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

    // Método público para refresh atualizado para usar o método central
    fun refresh() {
        loadHomeData()
    }
}

data class HomeUiState(
    val featuredArtists: List<Artist> = emptyList(),
    val recentAlbums: List<Album> = emptyList(),
    val isLoadingArtists: Boolean = false,
    val isLoadingAlbums: Boolean = false,
    val artistsError: String? = null,
    val albumsError: String? = null
)
