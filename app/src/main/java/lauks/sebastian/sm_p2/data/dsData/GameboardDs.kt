package lauks.sebastian.sm_p2.data.dsData

import lauks.sebastian.sm_p2.data.Sign

data class GameboardDs(var id: String, var gameId: String ,var board: MutableList<MutableList<Long>> = mutableListOf()) {

}