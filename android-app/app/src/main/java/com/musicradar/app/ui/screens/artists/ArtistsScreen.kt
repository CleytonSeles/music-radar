package com.musicradar.app.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.musicradar.app.ui.Screen
import com.musicradar.app.ui.components.ArtistCard
import com.musicradar.app.ui.components.EmptyState
import com.musicradar.app.ui.components.ErrorMessage
import com.musicradar.app.ui.components.LoadingIndicator
import com.musicradar.app.ui.components.MusicRadarTopAppBar
import com.musicradar.app.ui.components.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    navController: NavController,
    viewModel: ArtistsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    if (pullToRefreshState.isRefreshing) {
        viewModel.refresh()
        pullToRefreshState.endRefresh()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MusicRadarTopAppBar(
            title = "Artists",
            actions = {
                // Se precisarmos de ações na AppBar, podemos adicioná-las aqui
            }
        )

        var searchQuery by remember { mutableStateOf("") }
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = { viewModel.searchArtists(it) },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = "Search artists..."
        )

        Box(modifier = Modifier
            .weight(1f)
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator()
                }

                uiState.error != null -> {
                    ErrorMessage(
                        message = uiState.error ?: "An error occurred",
                        onRetry = { viewModel.refresh() }
                    )
                }

                uiState.artists.isEmpty() -> {
                    EmptyState(message = "No artists found")
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
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

                        item {
                            Spacer(modifier = Modifier.height(80.dp)) // Espaço para a barra de navegação
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
}
