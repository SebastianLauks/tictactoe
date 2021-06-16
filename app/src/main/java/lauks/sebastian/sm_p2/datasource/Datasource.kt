package lauks.sebastian.sm_p2.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.Gameboard
import lauks.sebastian.sm_p2.data.dsData.Converter
import lauks.sebastian.sm_p2.data.dsData.GameDs
import lauks.sebastian.sm_p2.data.dsData.GameboardDs

class Datasource {

    val TAG = "Datasource"
    val database = Firebase.database("https://tictactoe-78fc6-default-rtdb.firebaseio.com/")


    val gamesRef = database.getReference("games")
    val gameBoardsRef = database.getReference("gameboards")
    val bestRef = database.getReference("best")


    val gamesWithOnePlayer = MutableLiveData<MutableList<GameDs>>()
    val gameboardLD = MutableLiveData<Gameboard>()
    val gameLD = MutableLiveData<Game>()
    val bestFiveLD = MutableLiveData<List<Long>>()

    fun saveGame(game: Game) {
        Log.d(TAG, "Save game")

        val gameDs = Converter().gameToGameDs(game)

        if (gameDs.id == "") {
            val id = gamesRef.push().key
            gameDs.id = id!!
            gameDs.gameboardId = saveGameBoard(game.currentGameboard, game.onlineId)
            gamesRef.child(id!!).setValue(gameDs)
            game.onlineId = gameDs.id

        } else {
            gameDs.gameboardId = saveGameBoard(game.currentGameboard, game.onlineId)
            gamesRef.child(gameDs.id).setValue(gameDs)

        }



        observeGame(game.onlineId)
//        gameLD.value = game

//        val id = gamesRef.push().key
//        gameDs.id = id!!
//        gamesRef.child(id!!).setValue(gameDs)

    }

    fun saveGameAreTwoPlayers(gameId: String, areTwoPlayers: Boolean){
        gamesRef.child(gameId).child("areTwoPlayers").setValue(areTwoPlayers)
    }

    fun saveGameBoard(gameBoard: Gameboard, gameId: String): String {

        val gameBoardDs = Converter().gameboardToGameboardDs(gameBoard, gameId)

        if (gameBoardDs.id == "") {
            val id = gameBoardsRef.push().key
            gameBoardDs.id = id!!
            gameBoardsRef.child(id!!).setValue(gameBoardDs)
            gameBoard.onlineId = gameBoardDs.id
            gameboardLD.value = gameBoard
        } else {
            gameBoardsRef.child(gameBoardDs.id).setValue(gameBoardDs)
        }
        gameboardLD.value = gameBoard

        return gameBoardDs.id
    }

    fun getGamesWithOnePlayer() {
        gamesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var gamesMap: HashMap<String, HashMap<String, *>> = hashMapOf()
                if (snapshot.value != null)
                    gamesMap = snapshot.value as HashMap<String, HashMap<String, *>>
                val games = mutableListOf<GameDs>()

                gamesMap.forEach { t, u ->
                    if (!(u["areTwoPlayers"] as Boolean))
                        games.add(
                            GameDs(
                                u["id"] as String,
                                u["areTwoPlayers"] as Boolean,
                                u["gameboardId"] as String,
                                u["move"] as Long,
                                u["scorePlayerOne"] as Long,
                                u["scorePlayerTwo"] as Long,
                                u["gamePlaying"] as Boolean
                            )
                        )

                }
                gamesWithOnePlayer.value = games
            }

        })
    }

    fun observeGame(id: String) {
        gamesRef.orderByKey().equalTo(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    var gamesMap: HashMap<String, HashMap<String, *>> = hashMapOf()
                    if (snapshot.value != null) {
                        gamesMap = snapshot.value as HashMap<String, HashMap<String, *>>


                        var remoteGame = GameDs("", false, "-1", 1, 0, 0, false)
                        gamesMap.forEach { t, u ->
                            remoteGame = GameDs(
                                u["id"] as String,
                                u["areTwoPlayers"] as Boolean,
                                u["gameboardId"] as String,
                                u["move"] as Long,
                                u["scorePlayerOne"] as Long,
                                u["scorePlayerTwo"] as Long,
                                u["gamePlaying"] as Boolean
                            )
                        }

                        gameBoardsRef.orderByKey().equalTo(remoteGame.gameboardId)
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {
                                    Log.d(TAG, error.toString())
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var gamesMap: HashMap<String, HashMap<String, *>> = hashMapOf()
                                    if (snapshot.value != null) {
                                        gamesMap =
                                            snapshot.value as HashMap<String, HashMap<String, *>>

                                        var remoteGameboard = GameboardDs("", "", mutableListOf())
                                        gamesMap.forEach { t, u ->
                                            remoteGameboard = GameboardDs(
                                                u["id"] as String,
                                                u["gameId"] as String,
                                                u["board"] as MutableList<MutableList<Long>>
                                            )
                                        }
                                        gameboardLD.value =
                                            Converter().gameboardDsToGameboard(remoteGameboard)
                                        gameLD.value =
                                            Converter().gameDsToGame(remoteGame, remoteGameboard)
                                    }
                                }
                            })

                    }
                }
            })
    }

    fun deleteGame(id: String) {
        gamesRef.child(id).removeValue()
    }

    fun observeGameboard(id: String) {
        gameBoardsRef.orderByKey().equalTo(id)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val gamesMap = snapshot.value as HashMap<String, HashMap<String, *>>

                    var remoteGameboard = GameboardDs("", "", mutableListOf())
                    gamesMap.forEach { t, u ->
                        remoteGameboard = GameboardDs(
                            u["id"] as String,
                            u["gameId"] as String,
                            u["board"] as MutableList<MutableList<Long>>
                        )
                    }
                    gameboardLD.value = Converter().gameboardDsToGameboard(remoteGameboard)
                }
            })
    }

    fun getBestFive(){
        bestRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.value != null){
                        val list = snapshot.value as List<Long>
                        bestFiveLD.value = list
                    }else {
                        bestFiveLD.value = listOf()
                    }
            }

        })
    }

    fun saveBestFive(one: Long, two: Long){
        bestRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var list = mutableListOf<Long>()
                if (snapshot.value != null) {
                    list = snapshot.value as MutableList<Long>

                }
                list.add(one)
                list.add(two)
                list.sortDescending()
                bestRef.setValue(list.take(5))

            }
        })
    }
}