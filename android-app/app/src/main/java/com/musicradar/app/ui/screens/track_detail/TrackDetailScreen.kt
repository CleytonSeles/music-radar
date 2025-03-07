package com.musicradar.app.ui.screens.track_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musicradar.app.ui.components.LoadingIndicator

@Composable
fun TrackDetailScreen(
    trackId: Long,
    navController: NavController,
    viewModel: TrackDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            LoadingIndicator()
        } else {
            // Placeholder para implementação completa
            Text(
                text = "Track Detail Screen - ID: $trackId",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
