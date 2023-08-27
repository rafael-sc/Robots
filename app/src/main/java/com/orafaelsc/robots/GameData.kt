package com.orafaelsc.robots

import androidx.compose.ui.graphics.Color
import com.orafaelsc.robots.domain.BACKGROUND_COLOR
import com.orafaelsc.robots.domain.FIRST_PLAYER_BRIGHT_COLOR
import com.orafaelsc.robots.domain.FIRST_PLAYER_COLOR
import com.orafaelsc.robots.domain.GOAL_COLOR
import com.orafaelsc.robots.domain.SECOND_PLAYER_BRIGHT_COLOR
import com.orafaelsc.robots.domain.SECOND_PLAYER_COLOR

data class GameData(
    val firstPlayerSpots: MutableList<Int>,
    val firstPlayerWins: String = "0",
    val secondPlayerSpots: MutableList<Int>,
    val secondPlayerWins: String = "0",
    val goalSpot: Int = 0,
) {
    fun getBackgroundColorForSpot(spot: Int): Color = when {
        firstPlayerSpots.last() == spot -> FIRST_PLAYER_BRIGHT_COLOR
        firstPlayerSpots.contains(spot) -> FIRST_PLAYER_COLOR
        secondPlayerSpots.last() == spot -> SECOND_PLAYER_BRIGHT_COLOR
        secondPlayerSpots.contains(spot) -> SECOND_PLAYER_COLOR
        goalSpot == spot -> GOAL_COLOR
        else -> BACKGROUND_COLOR
    }
}
