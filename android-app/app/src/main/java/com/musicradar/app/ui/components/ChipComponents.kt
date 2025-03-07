package com.musicradar.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreChip(
    genre: String,
    onClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    selected: Boolean = false
) {
    ElevatedFilterChip(
        selected = selected,
        onClick = { onClick(genre) },
        label = { Text(genre) },
        modifier = modifier.padding(end = 8.dp),
        colors = FilterChipDefaults.elevatedFilterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun GenreList(
    genres: List<String>,
    selectedGenre: String? = null,
    onGenreClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(genres) { genre ->
            GenreChip(
                genre = genre,
                onClick = onGenreClick,
                selected = genre == selectedGenre
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = { onCategorySelected(category) },
        label = { Text(category) },
        modifier = modifier.padding(end = 4.dp),
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else null
    )
}

@Composable
fun PopularityTag(
    popularity: Int,
    modifier: Modifier = Modifier
) {
    val color = when {
        popularity >= 80 -> MaterialTheme.colorScheme.primary
        popularity >= 60 -> MaterialTheme.colorScheme.secondary
        popularity >= 40 -> MaterialTheme.colorScheme.tertiary
        else -> Color.Gray
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = when {
                popularity >= 80 -> "Popular"
                popularity >= 60 -> "Rising Star"
                popularity >= 40 -> "Notable"
                else -> "Underground"
            },
            color = color,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
