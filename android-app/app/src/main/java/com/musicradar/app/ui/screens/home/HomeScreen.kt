package com.musicradar.app.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musicradar.app.ui.Screen
import com.musicradar.app.ui.components.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        viewModel.refresh()
        pullToRefreshState.endRefresh()
    }

    Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome to MusicRadar",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Text(
                    text = "Featured Artists",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.isLoadingArtists) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.artistsError != null) {
                    // Corrigido para passar o parâmetro onRetry corretamente
                    ErrorMessage(
                        message = uiState.artistsError!!,
                        onRetry = { viewModel.refresh() }
                    )
                } else {
                    if (uiState.featuredArtists.isEmpty()) {
                        Text(
                            text = "No artists found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiState.featuredArtists.forEach { artist ->
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
                    }
                }
            }

            item {
                Text(
                    text = "Recent Albums",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (uiState.isLoadingAlbums) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (uiState.albumsError != null) {
                    // Corrigido para passar o parâmetro onRetry corretamente
                    ErrorMessage(
                        message = uiState.albumsError!!,
                        onRetry = { viewModel.refresh() }
                    )
                } else {
                    if (uiState.recentAlbums.isEmpty()) {
                        Text(
                            text = "No albums found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.recentAlbums) { album ->
                                AlbumCard(
                                    album = album,
                                    onClick = {
                                        album.id?.let {
                                            navController.navigate(Screen.AlbumDetail.createRoute(it))
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                // Corrigindo os comentários em português para inglês
                Spacer(modifier = Modifier.height(80.dp)) // Space for the bottom navigation bar
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

