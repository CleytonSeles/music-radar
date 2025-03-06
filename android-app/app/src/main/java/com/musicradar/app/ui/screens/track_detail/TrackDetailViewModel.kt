package com.musicradar.app.ui.screens.track_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musicradar.app.data.model.Track
import com.musicradar.app.data.repository.TrackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackDetailViewModel @Inject constructor(
    private val trackRepository: TrackRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val trackId: Long = checkNotNull(savedStateHandle["trackId"])

    private val _uiState = MutableStateFlow(TrackDetailUiState())
    val uiState: StateFlow<TrackDetailUiState> = _uiState.asStateFlow()

    init {
        loadTrack()
    }

    private fun loadTrack() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            trackRepository.getTrackById(trackId)
                .onSuccess { track ->
                    _uiState.update {
                        it.copy(
                            track = track,
                            isLoading = false
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            error = error.message ?: "Failed to load track",
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun refresh() {
        loadTrack()
    }
}

data class TrackDetailUiState(
    val track: Track? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
