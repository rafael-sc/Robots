package com.orafaelsc.robots.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.orafaelsc.robots.GameData

@Composable
fun GameGrid(gameData: GameData) {
    val rows = 7
    val columns = 7
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier,
        content = {
            items(rows * columns) { index ->

                val backgroundColor = gameData.getBackgroundColorForSpot(index)
                GridElement(backgroundColor)
            }
        },
    )
}
