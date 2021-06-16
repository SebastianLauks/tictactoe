package lauks.sebastian.sm_p2.data.dsData

import android.util.Log
import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.Gameboard
import lauks.sebastian.sm_p2.data.Sign

class Converter {

    fun gameDsToGame(gameDs: GameDs, gameboardDs: GameboardDs): Game{

        val game = Game()
        game.scorePlayerOne.value = gameDs.scorePlayerOne.toInt()
        game.scorePlayerTwo.value = gameDs.scorePlayerTwo.toInt()
        game.currentGameboard = gameboardDsToGameboard(gameboardDs)
        game.isGamePlaying = gameDs.isGamePlaying
        game.onlineId = gameDs.id
        game.move = gameDs.move.toInt()
        game.areTwoPlayers = gameDs.areTwoPlayers

        return game
    }

    fun gameboardDsToGameboard(gameboardDs: GameboardDs): Gameboard{
        val gameboard = Gameboard()
        var i =0
        gameboard.onlineId = gameboardDs.id
        gameboard.board.forEach { row ->
            var j = 0
            row.forEach { sign ->
               gameboard.board[i][j] = when(gameboardDs.board[i][j]){
                   0L -> {Sign.NOTHING}
                   1L -> {Sign.CIRCLE}
                   2L -> {Sign.CROSS}
                   else -> {Sign.NOTHING}
               }
                j++
            }
            i++
        }
        return gameboard
    }


    fun gameToGameDs(game:Game): GameDs{
        return GameDs(game.onlineId, game.areTwoPlayers, game.currentGameboard.onlineId, game.move.toLong(), game.scorePlayerOne.value?.toLong() ?: 0, game.scorePlayerTwo.value?.toLong() ?: 0, game.isGamePlaying)
    }

    fun gameboardToGameboardDs(gameboard: Gameboard, gameId: String): GameboardDs{
        val gameboardDs = GameboardDs(gameboard.onlineId, gameId, mutableListOf())



        for (i in 0 until gameboard.BOARD_SIZE) {
            gameboardDs.board.add(
                i,
                mutableListOf(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                )
            )
        }
        Log.d("TUTAJ",gameboard.board.size.toString())
        var i =0
        gameboard.board.forEach { row ->
            Log.d("TUTAJ_D",row.size.toString())
            var j = 0
            row.forEach { sign ->
                gameboardDs.board[i][j] = when(gameboard.board[i][j]){
                    Sign.NOTHING -> {0}
                    Sign.CIRCLE -> {1}
                    Sign.CROSS -> {2}
                }
                j++
            }
            i++
        }

        return gameboardDs
    }
}