package com.example.snakeandladder

const val maxPositionOnBoard = 200
const val minPositionOnBoard = 0
const val minValueOnDice = 2
const val maxValueOnDice = 12

fun main() {
    val snakes = getSnakes()
    val ladders = getLadders()
    val players = getPlayers()
    val board = createBoard(snakes, ladders, players)
    playGame(board)
}

fun getLadders(): List<Ladder> {

    val ladders = mutableListOf<Ladder>()
    var numberOfLadders = readNextInt()
    while (numberOfLadders-- != 0) {
        val (startIndex, endIndex) = readNextTwoInt()
        ladders.add(Ladder(startInd = startIndex, endInd = endIndex))
    }
    return ladders
}

fun getSnakes(): List<Snake> {

    val snakes = mutableListOf<Snake>()
    var numberOfSnakes = readNextInt()
    while (numberOfSnakes-- != 0) {
        val (startIndex, endIndex) = readNextTwoInt()
        snakes.add(Snake(startInd = startIndex, endInd = endIndex))
    }
    return snakes
}

fun getPlayers(): List<Player> {
    val players = mutableListOf<Player>()

    var numberOfPlayers = readNextInt()
    while (numberOfPlayers-- != 0) {
        val name = readLine()!!
        players.add(Player(name = name, position = 0))
    }
    return players
}

fun readNextInt() = readLine()!!.toInt()
fun readNextTwoInt() = readLine()!!.split(' ').map { it.toInt() }

fun createBoard(snakes: List<Snake>, ladders: List<Ladder>, players: List<Player>) = Board(
    ladders = ladders,
    snakes = snakes,
    startInd = minPositionOnBoard,
    endInd = maxPositionOnBoard,
    players = players,
    dice = Dice(minValue = minValueOnDice, maxValue = maxValueOnDice),
    turn = 0        //assuming that first player will always get the first turn
)


fun playGame(board: Board) {
    while (true) {
        if (didPlayerWin(board)) {
            break
        }
        val currentPosition = board.players[board.turn].position
        val nextMove = rollDice(board)
        moveAhead(nextMove, board)

        checkForLadder(board)
        checkForSnake(board)

        println("Player ${board.turn} rolled $nextMove and reached ${board.players[board.turn].position} from $currentPosition ")

        //player will get another chance if he/she gets max value on dice
        if (nextMove == maxValueOnDice)
            continue

        board.turn = (board.turn + 1) % board.players.size
    }
}

fun didPlayerWin(board: Board): Boolean {
    var playerWon = false
    for (player in board.players) {
        if (player.position >= maxPositionOnBoard) {
            println(player.name)
            playerWon = true
            break
        }
    }
    return playerWon
}

fun rollDice(board: Board) = board.dice.randomNumber(minValueOnDice, maxValueOnDice)

fun moveAhead(nextMove: Int, board: Board) {
    board.players[board.turn].position += nextMove
}

fun checkForLadder(board: Board) {
    for (element in board.ladders) {
        if (element.startInd == board.players[board.turn].position) {
            board.players[board.turn].position = element.endInd
            break
        }
    }
}

fun checkForSnake(board: Board) {
    for (element in board.snakes) {
        if (element.startInd == board.players[board.turn].position) {
            board.players[board.turn].position = element.endInd
            break
        }
    }
}