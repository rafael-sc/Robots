package com.orafaelsc.robots

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.orafaelsc.robots.extensions.randomItem
import kotlin.random.Random

data class GameData(
    val firstPlayerSpots: MutableList<Int>,
    val secondPlayerSpots: MutableList<Int>,
    var goalSpot: Int = 0,
) {
    private val lines = 7
    private val columns = 7
    fun getBackgroundColorForSpot(spot: Int): Color = when {
        firstPlayerSpots.contains(spot) -> Color(0xFFE91E63)
        secondPlayerSpots.contains(spot) -> Color(0xFF2C4CFF)
        goalSpot == spot -> Color(0xFFFFEB3B)
        else -> Color(0xFFDEDEDE)
    }

    companion object {
        private fun getRandomInt() = Random.nextInt(0, 50)

        fun GameData.newRound(): GameData {
            val first = mutableListOf<Int>()
            val nextSpotPlayer1 = getValidSpots(firstPlayerSpots, secondPlayerSpots).randomItem()
            if (nextSpotPlayer1 == null) {
                Log.d("ROBOTS!", "no more moves available for player 1")
            } else {
                if (nextSpotPlayer1 == goalSpot) {
                    Log.d("ROBOTS!", "player 1 wins!")
                }

                first.addAll(
                    firstPlayerSpots.toMutableList().apply {
                        add(nextSpotPlayer1)
                    },
                )
            }

            val second = mutableListOf<Int>()

            val nextSpotPlayer2 = getValidSpots(secondPlayerSpots, firstPlayerSpots).randomItem()
            if (nextSpotPlayer2 == null) {
                Log.d("ROBOTS!", "no more moves available for player 2")
            } else {
                if (nextSpotPlayer2 == goalSpot) {
                    Log.d("ROBOTS!", "player 2 wins!")
                }
                second.addAll(
                    secondPlayerSpots.toMutableList().apply {
                        add(nextSpotPlayer2)
                    },
                )
            }

            return GameData(
                goalSpot = this.goalSpot,
                firstPlayerSpots = first,
                secondPlayerSpots = second,
            )
        }

        fun newGame() = GameData(
            firstPlayerSpots = mutableListOf(6),
            secondPlayerSpots = mutableListOf(42),
            goalSpot = getRandomInt(),
        )
    }

    fun getValidSpots(
        playerUsedSpots: MutableList<Int>,
        opponentUsedSpots: MutableList<Int>,
    ): List<Int> {
        val lastSpot = playerUsedSpots.last()

        val actualLine = lastSpot / columns
        val actualColumn = lastSpot % columns
        val validSpots = mutableListOf<Int>()

        // check if exists a spot above
        if (actualLine > 0) {
            validSpots.add(lastSpot - lines)
        }
        // check if exists a spot under
        if (actualLine < lines - 1) {
            validSpots.add(lastSpot + lines)
        }

        // check if exists spot at left
        if (actualColumn > 0) {
            validSpots.add(lastSpot - 1)
        }

        // check if exists spot at right
        if (actualColumn < columns - 1) {
            validSpots.add(lastSpot + 1)
        }

        // remove used valid spots
        playerUsedSpots.forEach {
            if (validSpots.contains(it)) {
                validSpots.remove(it)
            }
        }
        opponentUsedSpots.forEach {
            if (validSpots.contains(it)) {
                validSpots.remove(it)
            }
        }

        validSpots.remove(lastSpot)
        Log.d("ROBOTS!", "Valid spots for index:$lastSpot = $validSpots")
        return validSpots
    }
}
