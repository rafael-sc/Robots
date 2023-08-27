package com.orafaelsc.robots.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orafaelsc.robots.GameData
import com.orafaelsc.robots.domain.COLUMNS
import com.orafaelsc.robots.domain.FIRST_PLAYER_COLOR
import com.orafaelsc.robots.domain.LINES
import com.orafaelsc.robots.domain.SECOND_PLAYER_COLOR

@Composable
fun GameGrid(gameData: GameData?, onNewGame: () -> Unit) {

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

                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.size(60.dp)) {

                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterStart),
                        text = gameData.secondPlayerWins,
                        fontSize = 16.sp,
                        color = SECOND_PLAYER_COLOR
                    )
                    GridElement(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterEnd),
                        backgroundColor = SECOND_PLAYER_COLOR
                    )
                }

                Box(modifier = Modifier.size(60.dp)) {
                    GridElement(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterStart),
                        backgroundColor = FIRST_PLAYER_COLOR
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.CenterEnd),
                        text = gameData.firstPlayerWins,
                        fontSize = 16.sp,
                        color = FIRST_PLAYER_COLOR
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            LazyVerticalGrid(
                modifier = Modifier.padding(start = 60.dp, end = 60.dp, top = 8.dp, bottom = 8.dp),
                columns = GridCells.Fixed(COLUMNS),
                content = {
                    items(LINES * COLUMNS) { index ->
                        val backgroundColor = gameData.getBackgroundColorForSpot(index)
                        GridElement(modifier = Modifier.padding(2.dp), backgroundColor)
                    }
                },
            )
        }

    }
}
