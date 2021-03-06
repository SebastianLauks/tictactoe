package lauks.sebastian.sm_p2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class Game{

    val POINTS_FOR_WIN = 3
    val POINTS_FOR_DRAW = 1

    val scorePlayerOne: MutableLiveData<Int> = MutableLiveData(0)
    val scorePlayerTwo: MutableLiveData<Int> = MutableLiveData(0)

    val gameboards: MutableList<Gameboard> = mutableListOf()
    var currentGameboard: Gameboard = Gameboard()
    var isGamePlaying = false
    var onlineId: String = ""
    var move: Int = 2
    var areTwoPlayers: Boolean = false

    fun start() {
        currentGameboard = Gameboard()
        isGamePlaying = true
        gameboards.add(currentGameboard)
    }

    fun getLastGameboard(): Gameboard {
        return gameboards.last()
    }

    fun playerOneWon(){
        scorePlayerOne.value = (scorePlayerOne.value ?: 0) + POINTS_FOR_WIN
        isGamePlaying = false
    }
    fun playerTwoWon(){
        scorePlayerTwo.value = (scorePlayerTwo.value?: 0) + POINTS_FOR_WIN
        isGamePlaying = false
    }
    fun draw(){
        scorePlayerOne.value = (scorePlayerOne.value?: 0) + POINTS_FOR_DRAW
        scorePlayerTwo.value = (scorePlayerTwo.value?: 0) + POINTS_FOR_DRAW
        isGamePlaying = false
    }



}