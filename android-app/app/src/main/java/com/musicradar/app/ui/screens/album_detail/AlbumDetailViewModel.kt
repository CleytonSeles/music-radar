package com.musicradar.app.ui.screens.album_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicradar.app.data.model.Album
import com.musicradar.app.data.model.Track
import com.musicradar.app.data.repository.AlbumRepository
import com.musicradar.app.data.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
    private val trackRepository: TrackRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val albumId: Long = checkNotNull(savedStateHandle["albumId"])

    private val _uiState = MutableStateFlow(AlbumDetailUiState())
    val uiState: StateFlow<AlbumDetailUiState> = _uiState.asStateFlow()

    init {
        loadAlbumData()
    }

    // Novo método privado que centraliza todas as chamadas de carregamento
    private fun loadAlbumData() {
        loadAlbum()
        loadAlbumTracks()
    }

    private fun loadAlbum() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingAlbum = true, albumError = null) }

            albumRepository.getAlbumById(albumId)
                .onSuccess { album ->
                    _uiState.update {
                        it.copy(
                            album = album,
                            isLoadingAlbum = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            albumError = error.message ?: "Failed to load album",
                            isLoadingAlbum = false
                        )
                    }
                }
        }
    }

    private fun loadAlbumTracks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingTracks = true, tracksError = null) }

            trackRepository.getTracksByAlbumId(albumId)
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

    // Método público para refresh atualizado para usar o método central
    fun refresh() {
        loadAlbumData()
    }
}

data class AlbumDetailUiState(
    val album: Album? = null,
    val tracks: List<Track> = emptyList(),
    val isLoadingAlbum: Boolean = false,
    val isLoadingTracks: Boolean = false,
    val albumError: String? = null,
    val tracksError: String? = null
)

