package com.orafaelsc.robots

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orafaelsc.robots.domain.ValidSpotsCase
import com.orafaelsc.robots.exceptions.NoMoreMovesException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RobotsViewModel(
    private val validSpotsCase: ValidSpotsCase = ValidSpotsCase(),
) : ViewModel() {
    var isAuto = false
        private set

    private val _goalSpot = MutableStateFlow<Int>(getAvailableSpotForGoal())
    val goalSpot: StateFlow<Int> = _goalSpot

    private val _firstPlayerSpots = MutableStateFlow<MutableList<Int>>(mutableStateListOf(6))
    val firstPlayerSpots: StateFlow<MutableList<Int>> = _firstPlayerSpots

    private val _secondPlayerSpots = MutableStateFlow<MutableList<Int>>(mutableStateListOf(42))
    val secondPlayerSpots: StateFlow<MutableList<Int>> = _secondPlayerSpots


    suspend fun goAuto() {
        while (isAuto) {
            firstPlayerMove()
            delay(500)
            secondPlayerMove()
            delay(500)
        }
    }

    fun newRound() {

        firstPlayerMove()
        secondPlayerMove()

    }

    private var firstPlayerCanMove: Boolean = true
    private var secondPlayerCanMove: Boolean = true

    private fun firstPlayerMove() {
        try {
            val nextSpotPlayer1 =
                validSpotsCase.getValidSpots(_firstPlayerSpots.value, _secondPlayerSpots.value)
            _firstPlayerSpots.value.add(nextSpotPlayer1)
            viewModelScope.launch {
                _firstPlayerSpots.emit(firstPlayerSpots.value)
            }

            if (nextSpotPlayer1 == goalSpot.value) {
                if (isAuto)
                    newGame()
                Log.d("ROBOTS!", "player 1 wins!")
            }
        } catch (e: NoMoreMovesException) {
            firstPlayerCanMove = false
            Log.d("ROBOTS!", "No more moves available")
        }
    }

    private fun secondPlayerMove() {
        try {

            val nextSpotPlayer2 =
                validSpotsCase.getValidSpots(secondPlayerSpots.value, firstPlayerSpots.value)

            _secondPlayerSpots.value.add(nextSpotPlayer2)

            viewModelScope.launch {
                _secondPlayerSpots.emit(secondPlayerSpots.value)
            }
            if (nextSpotPlayer2 == goalSpot.value) {

                if (isAuto)
                    newGame()
            }
        } catch (e: NoMoreMovesException) {
            secondPlayerCanMove = false
            Log.d("ROBOTS!", "No more moves available")
        }
    }


    fun newGame() {
        firstPlayerCanMove = true
        secondPlayerCanMove = true
        viewModelScope.launch {

            _firstPlayerSpots.emit(mutableListOf())
            _firstPlayerSpots.emit(mutableListOf(6))
            _secondPlayerSpots.emit(mutableListOf())
            _secondPlayerSpots.emit(mutableListOf(42))
            _goalSpot.emit(getAvailableSpotForGoal())
        }
    }

    private fun getAvailableSpotForGoal(): Int =
        (0 until 50).filter { it != 6 && it != 42 }.shuffled().first()

    fun toggleAutoMode() {
        isAuto = !isAuto
        viewModelScope.launch {
            goAuto()
        }
    }

}
