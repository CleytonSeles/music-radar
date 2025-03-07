package com.musicradar.app.util

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

/**
 * Extensões úteis para componentes do aplicativo
 */

/**
 * Verifica se a LazyColumn/LazyRow está sendo rolada
 */
@Composable
fun LazyListState.isScrollInProgress(): Boolean {
    val isScrollInProgress by remember {
        derivedStateOf { isScrollInProgress }
    }
    return isScrollInProgress
}

/**
 * Retorna apenas os primeiros N caracteres de uma string,
 * adicionando "..." se necessário
 */
fun String.truncate(maxLength: Int): String {
    return if (this.length <= maxLength) {
        this
    } else {
        "${this.take(maxLength)}..."
    }
}
