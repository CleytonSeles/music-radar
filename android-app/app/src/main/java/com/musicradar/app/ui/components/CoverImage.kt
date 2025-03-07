package com.musicradar.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun CoverImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholderIcon: ImageVector = Icons.Default.Album,
    aspectRatio: Float = 1f,
    contentScale: ContentScale = ContentScale.Crop,
    showGradientOverlay: Boolean = false
) {
    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clip(MaterialTheme.shapes.medium)
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        ) {
            val state = painter.state
            if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = placeholderIcon,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }
            } else {
                SubcomposeAsyncImageContent()
            }
        }

        if (showGradientOverlay) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }
    }
}

@Composable
fun ArtistCoverImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    showGradientOverlay: Boolean = false
) {
    CoverImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        placeholderIcon = Icons.Default.Person,
        showGradientOverlay = showGradientOverlay
    )
}

@Composable
fun AlbumCoverImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    showGradientOverlay: Boolean = false
) {
    CoverImage(
        imageUrl = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        placeholderIcon = Icons.Default.Album,
        showGradientOverlay = showGradientOverlay
    )
}
