package com.orafaelsc.robots.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        Column(Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.background(Color.Black).size(80.dp)) {
                    val p1Color = Color(0xFFE91E63)
                    GridElement(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterStart),
                        backgroundColor = p1Color
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterEnd),
                        text = gameData.firstPlayerWins,
                        fontSize = 16.sp,
                        color = p1Color
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.background(Color.Black).size(80.dp)) {
                    val p2Color = Color(0xFF2C4CFF)
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterStart),
                        text = gameData.secondPlayerWins,
                        fontSize = 16.sp,
                        color = p2Color
                    )
                    GridElement(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterEnd),
                        backgroundColor = p2Color
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier,
                content = {
                    items(rows * columns) { index ->
                        val backgroundColor = gameData.getBackgroundColorForSpot(index)
                        GridElement(modifier = Modifier.padding(2.dp), backgroundColor)
                    }
                },
            )
        }

    }
}
