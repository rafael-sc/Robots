package com.orafaelsc.robots

import androidx.compose.ui.graphics.Color


data class GameData(
    val firstPlayerSpots: MutableList<Int>,
    val firstPlayerWins: String = "0",
    val secondPlayerSpots: MutableList<Int>,
    val secondPlayerWins: String = "0",
    val goalSpot: Int = 0,
) {
    fun getBackgroundColorForSpot(spot: Int): Color = when {
        firstPlayerSpots.contains(spot) -> Color(0xFFE91E63)
        secondPlayerSpots.contains(spot) -> Color(0xFF2C4CFF)
        goalSpot == spot -> Color(0xFFFFEB3B)
        else -> Color(0xFFDEDEDE)
    }
}
