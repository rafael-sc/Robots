package com.orafaelsc.robots.domain


import com.orafaelsc.robots.exceptions.NoMoreMovesException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class ValidSpotsUseCaseTest {

    private val validSpotsUseCase: ValidSpotsUseCase = ValidSpotsUseCase()

    @Test
    fun testValidSpotsBasic() {
        val playerUsedSpots = mutableListOf(6, 13, 12)
        val opponentUsedSpots = mutableListOf(42, 43, 44)
        val result = validSpotsUseCase.getValidSpots(playerUsedSpots, opponentUsedSpots)
        assert(result in listOf(5, 11, 19))
    }

    @Test
    fun testValidSpotsAllUsed() {
        val playerUsedSpots = (1..48).toMutableList()
        val opponentUsedSpots = mutableListOf(0)

        val thrown = assertThrows(
            NoMoreMovesException::class.java,
            { validSpotsUseCase.getValidSpots(playerUsedSpots, opponentUsedSpots) },
            "Expected doThing() to throw, but it didn't"
        )

        assertTrue(thrown is NoMoreMovesException)
    }


}