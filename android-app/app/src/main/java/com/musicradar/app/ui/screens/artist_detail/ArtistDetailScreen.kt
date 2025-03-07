package com.musicradar.app.ui.screens.artist_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musicradar.app.ui.Screen
import com.musicradar.app.ui.components.AlbumCard
import com.musicradar.app.ui.components.CoverImage
import com.musicradar.app.ui.components.ErrorMessage
import com.musicradar.app.ui.components.LoadingIndicator
import com.musicradar.app.ui.components.SectionHeader
import com.musicradar.app.ui.components.TrackItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    artistId: Long,
    navController: NavController,
    viewModel: ArtistDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        viewModel.refresh()
        pullToRefreshState.endRefresh()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        when {
            uiState.artistError != null -> {
                ErrorMessage(
                    message = uiState.artistError ?: "Failed to load artist",
                    onRetry = { viewModel.refresh() }
                )
            }

            uiState.isLoadingArtist -> {
                LoadingIndicator()
            }

            else -> {
                val artist = uiState.artist

                if (artist != null) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        // Artist Header
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                CoverImage(
                                    imageUrl = artist.imageUrl,
                                    contentDescription = artist.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    contentScale = ContentScale.Crop,
                                    showGradientOverlay = true
                                )

                                TopAppBar(
                                    title = { },
                                    navigationIcon = {
                                        IconButton(onClick = { navController.navigateUp() }) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                                contentDescription = "Back",
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    },
                                    actions = {
                                        // Placeholder for favorite button
                                        IconButton(onClick = { }) {
                                            Icon(
                                                imageVector = Icons.Default.FavoriteBorder,
                                                contentDescription = "Favorite",
                                                tint = MaterialTheme.colorScheme.onPrimary
                                            )
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )

                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = artist.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )

                                    artist.genres?.let {
                                        Text(
                                            text = it,
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                                        )
                                    }
                                }
                            }
                        }

                        // Artist Bio
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                if (!artist.bio.isNullOrEmpty()) {
                                    Text(
                                        text = "Biography",
                                        style = MaterialTheme.typography.titleLarge
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = artist.bio,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    HorizontalDivider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 16.dp)
                                    )
                                }
                            }
                        }

                        // Albums Section
                        item {
                            SectionHeader(
                                title = "Albums",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            when {
                                uiState.isLoadingAlbums -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                uiState.albumsError != null -> {
                                    Text(
                                        text = "Failed to load albums",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }

                                uiState.albums.isEmpty() -> {
                                    Text(
                                        text = "No albums found",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }

                                else -> {
                                    LazyRow(
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    ) {
                                        items(uiState.albums) { album ->
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

                        // Popular Tracks Section
                        item {
                            SectionHeader(
                                title = "Popular Tracks",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )

                            when {
                                uiState.isLoadingTracks -> {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                }

                                uiState.tracksError != null -> {
                                    Text(
                                        text = "Failed to load tracks",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }

                                uiState.tracks.isEmpty() -> {
                                    Text(
                                        text = "No tracks found",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                }

                                else -> {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 16.dp)
                                    ) {
                                        uiState.tracks.take(5).forEach { track ->
                                            TrackItem(
                                                track = track,
                                                onClick = {
                                                    track.id?.let {
                                                        navController.navigate(Screen.TrackDetail.createRoute(it))
                                                    }
                                                },
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
