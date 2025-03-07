package com.musicradar.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.musicradar.app.ui.screens.album_detail.AlbumDetailScreen
import com.musicradar.app.ui.screens.albums.AlbumsScreen
import com.musicradar.app.ui.screens.artist_detail.ArtistDetailScreen
import com.musicradar.app.ui.screens.artists.ArtistsScreen
import com.musicradar.app.ui.screens.home.HomeScreen
import com.musicradar.app.ui.screens.search.SearchScreen
import com.musicradar.app.ui.screens.track_detail.TrackDetailScreen

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Artists : Screen("artists")
    data object Albums : Screen("albums")
    data object Search : Screen("search")

    data object ArtistDetail : Screen("artist/{artistId}") {
        fun createRoute(artistId: Long) = "artist/$artistId"
    }

    data object AlbumDetail : Screen("album/{albumId}") {
        fun createRoute(albumId: Long) = "album/$albumId"
    }

    data object TrackDetail : Screen("track/{trackId}") {
        fun createRoute(trackId: Long) = "track/$trackId"
    }
}

@Composable
fun MusicRadarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Artists.route) {
            ArtistsScreen(navController = navController)
        }

        composable(Screen.Albums.route) {
            AlbumsScreen(navController = navController)
        }

        composable(Screen.Search.route) {
            SearchScreen(navController = navController)
        }

        composable(
            route = Screen.ArtistDetail.route,
            arguments = listOf(navArgument("artistId") { type = NavType.LongType })
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getLong("artistId") ?: return@composable
            ArtistDetailScreen(
                artistId = artistId,
                navController = navController
            )
        }

        composable(
            route = Screen.AlbumDetail.route,
            arguments = listOf(navArgument("albumId") { type = NavType.LongType })
        ) { backStackEntry ->
            val albumId = backStackEntry.arguments?.getLong("albumId") ?: return@composable
            AlbumDetailScreen(
                albumId = albumId,
                navController = navController
            )
        }

        composable(
            route = Screen.TrackDetail.route,
            arguments = listOf(navArgument("trackId") { type = NavType.LongType })
        ) { backStackEntry ->
            val trackId = backStackEntry.arguments?.getLong("trackId") ?: return@composable
            TrackDetailScreen(
                trackId = trackId,
                navController = navController
            )
        }
    }
}


