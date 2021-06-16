package lauks.sebastian.sm_p2.viewmodel

import androidx.lifecycle.ViewModel
import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.Gameboard
import lauks.sebastian.sm_p2.datasource.Datasource

class OnlineGameViewModel: ViewModel() {

    var myPlayerNumber = -1

    val datasource = Datasource()

    fun saveGame(game: Game) {
        datasource.saveGame(game)
    }

//    fun saveGameBoard(gameboard: Gameboard, gameId: String){
//        datasource.saveGameBoard(gameboard, gameId)
//    }

    fun observeGame(gameId: String) = datasource.observeGame(gameId)

    fun getGamesWithOnePlayer() = datasource.getGamesWithOnePlayer()

    fun gamesWithOnePlayer() = datasource.gamesWithOnePlayer

    fun getGameLD() = datasource.gameLD

}