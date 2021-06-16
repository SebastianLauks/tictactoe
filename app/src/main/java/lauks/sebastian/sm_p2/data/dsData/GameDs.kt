package lauks.sebastian.sm_p2.data.dsData

import lauks.sebastian.sm_p2.data.Gameboard

data class GameDs(var id: String, var areTwoPlayers:Boolean = false, var gameboardId: String, var move: Long, var scorePlayerOne: Long, var scorePlayerTwo: Long, var isGamePlaying: Boolean) {
}