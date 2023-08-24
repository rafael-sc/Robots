package com.orafaelsc.robots.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameGrid() {
    val rows = 7
    val columns = 7
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier.fillMaxSize(),
        content = {
            items(rows * columns) { index ->
                GridElement()
            }
        },
    )
}
