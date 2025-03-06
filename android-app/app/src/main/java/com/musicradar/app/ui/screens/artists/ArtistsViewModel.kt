package com.musicradar.app.ui.screens.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicradar.app.data.model.Artist
import com.musicradar.app.data.repository.ArtistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistsViewModel @Inject constructor(
    private val artistRepository: ArtistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistsUiState())
    val uiState: StateFlow<ArtistsUiState> = _uiState.asStateFlow()

    init {
        loadArtists()
    }

    fun loadArtists() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            artistRepository.getAllArtists()
                .onSuccess { artists ->
                    _uiState.update {
                        it.copy(
                            artists = artists,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Failed to load artists",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun searchArtists(query: String) {
        if (query.isEmpty()) {
            loadArtists()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            artistRepository.searchArtistsByName(query)
                .onSuccess { artists ->
                    _uiState.update {
                        it.copy(
                            artists = artists,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Failed to search artists",
                            isLoading = false
                        )
                    }
                }
        }
    }
}

data class ArtistsUiState(
    val artists: List<Artist> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
