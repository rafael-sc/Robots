package com.orafaelsc.robots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.orafaelsc.robots.ui.components.GameGrid
import com.orafaelsc.robots.ui.theme.RobotsTheme

class RobotsActivity : ComponentActivity() {

    val viewModel by viewModels<RobotsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RobotsTheme {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(
                            modifier = Modifier.padding(40.dp),
                            onClick = {
                                viewModel.toggleAutoMode()
                            },
                        ) {
                            Text(text = "Auto Mode")
                        }
                        Button(
                            modifier = Modifier.padding(40.dp),
                            onClick = {
                                viewModel.newRound()
                            },
                        ) {
                            Text(text = "New Round")
                        }
                        CreateGameGrid(viewModel = viewModel)
                    }
                }
            }
        }
    }

    @Composable
    private fun CreateGameGrid(viewModel: RobotsViewModel) {

        val gameData = GameData(
            firstPlayerSpots = viewModel.firstPlayerSpots.collectAsState().value,
            secondPlayerSpots = viewModel.secondPlayerSpots.collectAsState().value,
            goalSpot = viewModel.goalSpot.collectAsState().value,
        )
        GameGrid(gameData) {
            viewModel.newGame()
        }
    }
}
