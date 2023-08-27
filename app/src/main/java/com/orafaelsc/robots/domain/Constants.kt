package com.orafaelsc.robots.domain

import androidx.compose.ui.graphics.Color

const val LINES = 7
const val COLUMNS = 7
const val FIRST_PLAYER_START_SPOT = COLUMNS -1
const val SECOND_PLAYER_START_SPOT = LINES * COLUMNS - COLUMNS
const val INITIAL_SPOT = 0

val FIRST_PLAYER_COLOR = Color(0xFFE91E63)
val FIRST_PLAYER_BRIGHT_COLOR = Color(0xFFE91E9F)
val SECOND_PLAYER_COLOR = Color(0xFF2C4CFF)
val SECOND_PLAYER_BRIGHT_COLOR = Color(0xFF2C79FF)
val GOAL_COLOR = Color(0xFFFFEB3B)
val BACKGROUND_COLOR = Color(0xFFDEDEDE)