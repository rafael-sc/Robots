package com.orafaelsc.robots

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class GameData(
    val firstPlayerSpots: MutableList<Int>,
    val secondPlayerSpots: MutableList<Int>,
    var goalSpot: Int = 0,
) {
    private fun getBackgroundColor(index: Int): Color = when {
        firstPlayerSpots.contains(index) -> Color(0xFFE91E63)
        secondPlayerSpots.contains(index) -> Color(0xFF2C4CFF)
        goalSpot == index -> Color(0xFFFFEB3B)
        else -> Color(0xFFDEDEDE)
    }

    fun getElementData(index: Int) = ElementData(
        index = index,
        backgroundColor = getBackgroundColor(index),
    )

    companion object {
        private fun getRandomInt() = Random.nextInt(0, 50)
        fun GameData.newRound(): GameData {
            val first = firstPlayerSpots.toMutableList().apply { add(getRandomInt()) }
            val second = secondPlayerSpots.toMutableList().apply { add(getRandomInt()) }
            secondPlayerSpots.add(getRandomInt())

            return GameData(
                goalSpot = this.goalSpot,
                firstPlayerSpots = first,
                secondPlayerSpots = second,
            )
        }

        fun newGame() = GameData(
            firstPlayerSpots = mutableListOf(getRandomInt()),
            secondPlayerSpots = mutableListOf(getRandomInt()),
            goalSpot = getRandomInt(),
        )
    }
}
