package com.orafaelsc.robots.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

                val elementData = gameData.getElementData(index)

                val backgroundColor =
                    if (index == elementData.index) {
                        elementData.backgroundColor
                    } else {
                        Color(0xFFDEDEDE)
                    }
                GridElement(backgroundColor)
            }
        },
    )
}
