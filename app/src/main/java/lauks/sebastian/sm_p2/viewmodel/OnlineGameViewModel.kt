package lauks.sebastian.sm_p2.viewmodel

import androidx.lifecycle.ViewModel
import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.Gameboard
import lauks.sebastian.sm_p2.data.dsData.GameDs
import lauks.sebastian.sm_p2.datasource.Datasource

class OnlineGameViewModel: ViewModel() {

    var myPlayerNumber = -1

    val datasource = Datasource()

    var newestBestFive: List<Long> = listOf()

    fun saveGame(game: Game) {
        datasource.saveGame(game)
    }

    fun saveGameAreTwoPlayers(gameId: String, areTwoPlayers: Boolean) = datasource.saveGameAreTwoPlayers(gameId, areTwoPlayers)

//    fun saveGameBoard(gameboard: Gameboard, gameId: String){
//        datasource.saveGameBoard(gameboard, gameId)
//    }

    fun observeGame(gameId: String) = datasource.observeGame(gameId)

    fun getGamesWithOnePlayer() = datasource.getGamesWithOnePlayer()

    fun gamesWithOnePlayer() = datasource.gamesWithOnePlayer

    fun getGameLD() = datasource.gameLD

    fun deleteGame(id: String) = datasource.deleteGame(id)

    fun saveBestFive(one:Long, two:Long) = datasource.saveBestFive(one, two)

    fun getBestFive() = datasource.getBestFive()

    fun bestFiveLD() = datasource.bestFiveLD

}