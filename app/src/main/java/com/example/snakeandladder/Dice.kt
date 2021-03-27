package com.example.snakeandladder

import kotlin.random.Random

data class Dice (
    val minValue : Int = 1,
    val maxValue : Int = 6
) {
    fun randomNumber(minValue: Int, maxValue: Int) = Random.nextInt(maxValue) + minValue
}