package lauks.sebastian.sm_p2.repository

import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.Sign

class GameRepository private constructor(){

    private var board = mutableListOf<MutableList<Sign>>()

    private var game = Game()

    var closedGame = false

    fun getGame() = game

    fun saveGame(game: Game) {
        this.game = game
    }

    fun getBoard() = board

    fun saveBoard(board: MutableList<MutableList<Sign>>){
        this.board = board
    }

    companion object{
        @Volatile private  var instance: GameRepository ? = null

        fun getInstance(): GameRepository  =
            instance ?: synchronized(this) {
                instance ?: GameRepository ().also { instance = it }
            }

    }
}
