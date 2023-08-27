package com.orafaelsc.robots.domain

import android.util.Log
import com.orafaelsc.robots.exceptions.NoMoreMovesException
import com.orafaelsc.robots.extensions.randomItem

class ValidSpotsCase {

    private val lines = 7
    private val columns = 7

    fun getValidSpots(
        playerUsedSpots: MutableList<Int>,
        opponentUsedSpots: MutableList<Int>,
    ): Int {
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
        return validSpots.randomItem() ?: throw NoMoreMovesException()
    }
}