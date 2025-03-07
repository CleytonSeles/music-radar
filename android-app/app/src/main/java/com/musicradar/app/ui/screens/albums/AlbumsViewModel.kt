package com.musicradar.app.ui.screens.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicradar.app.data.model.Album
import com.musicradar.app.data.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumsUiState())
    val uiState: StateFlow<AlbumsUiState> = _uiState.asStateFlow()

    init {
        loadAlbums()
    }

    // Alterado para private - agora só pode ser chamado dentro desta classe
    private fun loadAlbums() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            albumRepository.getAllAlbums()
                .onSuccess { albums ->
                    _uiState.update {
                        it.copy(
                            albums = albums,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Failed to load albums",
                            isLoading = false
                        )
                    }
                }
        }
    }

    // Adicionando uma função pública para atualizar os dados
    fun refresh() {
        loadAlbums()
    }

    fun searchAlbums(query: String) {
        if (query.isEmpty()) {
            loadAlbums()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            albumRepository.searchAlbumsByTitle(query)
                .onSuccess { albums ->
                    _uiState.update {
                        it.copy(
                            albums = albums,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Failed to search albums",
                            isLoading = false
                        )
                    }
                }
        }
    }
}

data class AlbumsUiState(
    val albums: List<Album> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

