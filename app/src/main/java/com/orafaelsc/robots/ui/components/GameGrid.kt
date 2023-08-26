package com.orafaelsc.robots.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.orafaelsc.robots.GameData

@Composable
fun GameGrid(gameData: GameData?, onNewGame: () -> Unit) {

    val rows = 7
    val columns = 7


    if (gameData == null) {
        Button(onClick = onNewGame) {
            Text(text = "New game")
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier,
            content = {
                items(rows * columns) { index ->
                    val backgroundColor =
                        gameData.getBackgroundColorForSpot(index) ?: Color(0xFFDEDEDE)
                    GridElement(backgroundColor)
                }
            },
        )
    }
}
