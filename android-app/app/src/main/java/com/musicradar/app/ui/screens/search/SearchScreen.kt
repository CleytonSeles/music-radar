package com.musicradar.app.ui.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musicradar.app.ui.Screen
import com.musicradar.app.ui.components.AlbumListItem
import com.musicradar.app.ui.components.ArtistCard
import com.musicradar.app.ui.components.EmptyState
import com.musicradar.app.ui.components.ErrorMessage
import com.musicradar.app.ui.components.LoadingIndicator
import com.musicradar.app.ui.components.MusicRadarTopAppBar
import com.musicradar.app.ui.components.SearchBar
import com.musicradar.app.ui.components.SectionHeader
import com.musicradar.app.ui.components.TrackItem

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MusicRadarTopAppBar(
            title = "Search"
        )

        var searchQuery by remember { mutableStateOf("") }
        SearchBar(
            query = searchQuery,
            onQueryChange = {
                searchQuery = it
                if (searchQuery.isEmpty()) {
                    viewModel.clearSearch()
                }
            },
            onSearch = {
                viewModel.search(it)
            },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = "Search artists, albums, tracks..."
        )

        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }

            uiState.error != null -> {
                ErrorMessage(
                    message = uiState.error ?: "An error occurred",
                    onRetry = { viewModel.search(searchQuery) }
                )
            }

            searchQuery.isEmpty() -> {
                EmptyState(message = "Search for artists, albums, and tracks")
            }

            uiState.artists.isEmpty() && uiState.albums.isEmpty() && uiState.tracks.isEmpty() -> {
                EmptyState(message = "No results found for '$searchQuery'")
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (uiState.artists.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Artists")
                        }

                        items(uiState.artists) { artist ->
                            ArtistCard(
                                artist = artist,
                                onClick = {
                                    artist.id?.let {
                                        navController.navigate(Screen.ArtistDetail.createRoute(it))
                                    }
                                }
                            )
                        }
                    }

                    if (uiState.albums.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Albums")
                        }

                        items(uiState.albums) { album ->
                            AlbumListItem(
                                album = album,
                                onClick = {
                                    album.id?.let {
                                        navController.navigate(Screen.AlbumDetail.createRoute(it))
                                    }
                                }
                            )
                        }
                    }

                    if (uiState.tracks.isNotEmpty()) {
                        item {
                            SectionHeader(title = "Tracks")
                        }

                        items(uiState.tracks) { track ->
                            TrackItem(
                                track = track,
                                onClick = {
                                    track.id?.let {
                                        navController.navigate(Screen.TrackDetail.createRoute(it))
                                    }
                                }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp)) // Espaço para a barra de navegação
                    }
                }
            }
        }
    }
}
