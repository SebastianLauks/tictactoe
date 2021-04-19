package lauks.sebastian.sm_p2.data

class Game(val singlePlayer: Boolean) {

    val POINTS_FOR_WIN = 3
    val POINTS_FOR_DRAW = 1

    var scorePlayerOne = 0
    var scorePlayerTwo = 0

    val gameboards: MutableList<Gameboard> = mutableListOf()
    lateinit var currentGameboard: Gameboard

    fun start() {
        currentGameboard = Gameboard()
        gameboards.add(currentGameboard)
    }

    fun playerOneWon(){
        scorePlayerOne += POINTS_FOR_WIN
    }
    fun playerTwoWon(){
        scorePlayerTwo += POINTS_FOR_WIN
    }
    fun draw(){
        scorePlayerOne += POINTS_FOR_DRAW
        scorePlayerTwo += POINTS_FOR_DRAW
    }



}