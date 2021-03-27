package com.example.snakeandladder

data class Board (
    val ladders : List<Ladder>,
    val snakes : List<Snake>,
    val startInd : Int = 0,
    val endInd : Int = 100,
    val players: List<Player>,
    val dice : Dice,
    var turn : Int = 0
)