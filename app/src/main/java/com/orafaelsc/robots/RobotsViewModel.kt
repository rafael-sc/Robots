package com.orafaelsc.robots

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orafaelsc.robots.domain.ValidSpotsCase
import com.orafaelsc.robots.domain.COLUMNS
import com.orafaelsc.robots.domain.FIRST_PLAYER_START_SPOT
import com.orafaelsc.robots.domain.LINES
import com.orafaelsc.robots.domain.SECOND_PLAYER_START_SPOT
import com.orafaelsc.robots.domain.INITIAL_SPOT
import com.orafaelsc.robots.exceptions.NoMoreMovesException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

import kotlinx.coroutines.launch

class RobotsViewModel(
    private val validSpotsCase: ValidSpotsCase = ValidSpotsCase(),
) : ViewModel() {

    private var isAuto = false
    val gameData = MutableSharedFlow<GameData?>(1)
    private var goalSpot: Int = getAvailableSpotForGoal()
    private val firstPlayerSpots: MutableList<Int> = mutableStateListOf(FIRST_PLAYER_START_SPOT)
    private var firstPlayerWins = 0
    private val secondPlayerSpots: MutableList<Int> = mutableStateListOf(SECOND_PLAYER_START_SPOT)
    private var secondPlayerWins = 0
    private var firstPlayerCanMove: Boolean = true
    private var secondPlayerCanMove: Boolean = true

    init {
        emitGameData()
    }

    private suspend fun goAuto() {
        while (isAuto) {
            playerMove(firstPlayerSpots, secondPlayerSpots, true)
            playerMove(secondPlayerSpots, firstPlayerSpots, false)
        }
    }

    private suspend fun playerMove(
        playerSpots: MutableList<Int>,
        opponentSpots: MutableList<Int>,
        isFirstPlayer: Boolean
    ) {
        try {
            val nextSpot = validSpotsCase.getValidSpots(playerSpots, opponentSpots)
            playerSpots.add(nextSpot)

            if (nextSpot == goalSpot) {
                viewModelScope.launch {
                    if (isFirstPlayer)
                        firstPlayerWins += 1
                    else
                        secondPlayerWins += 1

                    emitGameData()
                    if (isAuto)
                        newGame()

                    Log.d("ROBOTS!", "player ${if (isFirstPlayer) 1 else 2} wins!")
                }
            } else {
                emitGameData()
            }

        } catch (e: NoMoreMovesException) {
            if (isFirstPlayer)
                firstPlayerCanMove = false
            else
                secondPlayerCanMove = false
            Log.d("ROBOTS!", "No more moves available for player ${if (isFirstPlayer) 1 else 2}")
            checkIfGameIsOver()
        }
        delay(500)
    }


    private fun checkIfGameIsOver() {
        if (!firstPlayerCanMove && !secondPlayerCanMove) {
            newGame()
        }
    }

    fun newGame() {
        Log.d("ROBOTS!", "New Game")
        firstPlayerCanMove = true
        secondPlayerCanMove = true
        firstPlayerSpots.clear()
        firstPlayerSpots.add(6)
        secondPlayerSpots.clear()
        secondPlayerSpots.add(42)
        goalSpot = getAvailableSpotForGoal()
        emitGameData()
    }

    private fun emitGameData() {
        viewModelScope.launch {
            gameData.emit(
                GameData(
                    firstPlayerSpots,
                    firstPlayerWins.toString(),
                    secondPlayerSpots,
                    secondPlayerWins.toString(),
                    goalSpot
                )
            )
        }
    }

    private fun getAvailableSpotForGoal(): Int =
        (INITIAL_SPOT until LINES * COLUMNS).filter { it != FIRST_PLAYER_START_SPOT && it != SECOND_PLAYER_START_SPOT }
            .shuffled().first()

    fun toggleAutoMode() {
        isAuto = !isAuto
        viewModelScope.launch {
            goAuto()
        }
    }
}
