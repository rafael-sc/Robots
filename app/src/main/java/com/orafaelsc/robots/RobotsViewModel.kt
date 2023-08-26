package com.orafaelsc.robots

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orafaelsc.robots.extensions.randomItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class RobotsViewModel : ViewModel() {

    private val _goalSpot = MutableStateFlow<Int>(getAvailableSpotForGoal())
    val goalSpot: StateFlow<Int> = _goalSpot

    private val _firstPlayerSpots = MutableStateFlow<MutableList<Int>>(mutableStateListOf(6))
    val firstPlayerSpots: StateFlow<MutableList<Int>> = _firstPlayerSpots

    private val _secondPlayerSpots = MutableStateFlow<MutableList<Int>>(mutableStateListOf(42))
    val secondPlayerSpots: StateFlow<MutableList<Int>> = _secondPlayerSpots


    private val lines = 7
    private val columns = 7

    fun newRound() {

        val nextSpotPlayer1 =
            getValidSpots(_firstPlayerSpots.value, _secondPlayerSpots.value).randomItem()
        if (nextSpotPlayer1 == null) {
            Log.d("ROBOTS!", "no more moves available for player 1")
        } else {
            if (nextSpotPlayer1 == goalSpot.value) {
                Log.d("ROBOTS!", "player 1 wins!")
            }
            _firstPlayerSpots.value.add(nextSpotPlayer1)
        }

        val nextSpotPlayer2 =
            getValidSpots(secondPlayerSpots.value, firstPlayerSpots.value).randomItem()
        if (nextSpotPlayer2 == null) {
            Log.d("ROBOTS!", "no more moves available for player 2")
        } else {
            if (nextSpotPlayer2 == goalSpot.value) {
                Log.d("ROBOTS!", "player 2 wins!")
            }
            _secondPlayerSpots.value.add(nextSpotPlayer2)
        }

        viewModelScope.launch {
            _firstPlayerSpots.emit(firstPlayerSpots.value)
            _secondPlayerSpots.emit(secondPlayerSpots.value)
        }
    }

    private fun getValidSpots(
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

    fun newGame() {
        viewModelScope.launch {
            _firstPlayerSpots.emit( mutableListOf())
            _firstPlayerSpots.emit( mutableListOf(6))
            _secondPlayerSpots.emit( mutableListOf())
            _secondPlayerSpots.emit( mutableListOf(42))
            _goalSpot.emit(getAvailableSpotForGoal())
        }
    }

    private fun getAvailableSpotForGoal(): Int =
        (0 until 50).filter { it != 6 && it != 42 }.shuffled().first()


}
