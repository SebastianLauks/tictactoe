package lauks.sebastian.sm_p2.view

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_gameboard.*
import lauks.sebastian.sm_p2.R
import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.MoveOutput
import lauks.sebastian.sm_p2.data.Sign
import lauks.sebastian.sm_p2.utils.CustomDialogGenerator
import lauks.sebastian.sm_p2.viewmodel.GameViewModel
import java.lang.Integer.min
import kotlin.random.Random

class GameboardActivity : AppCompatActivity() {


    val TAG = "GameboardActivity"
    lateinit var game: Game
    private lateinit var gameViewModel: GameViewModel
    private var layoutBoard = mutableListOf<MutableList<ImageView>>(
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>(),
        mutableListOf<ImageView>()
        )

    private var singlePlayer: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameboard)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        singlePlayer = intent.extras?.get("singlePlayer") as Boolean


        game = gameViewModel.getGame()
        if(gameViewModel.getClosedGame()){
            gameViewModel.setClosedGame(false)
            game = Game()
        }
        if (!game.isGamePlaying) game.start()


        game.scorePlayerOne.observe(this, Observer {
            tvScorePlayerOne.text = it?.toString() ?: "0"
        })

        game.scorePlayerTwo.observe(this, Observer {
            tvScorePlayerTwo.text = it?.toString() ?: "0"
        })

        createLayouts()
        updateLayout()
    }


    override fun onStop() {
        gameViewModel.saveGame(game)
        super.onStop()
    }

    override fun onDestroy() {
        if(singlePlayer){
            val score = game.scorePlayerOne.value ?: 0

            val sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)
            if(sharedPreferences != null){
                val score1 = sharedPreferences.getInt("score1", 0)
                val score2 = sharedPreferences.getInt("score2", 0)
                val score3 = sharedPreferences.getInt("score3", 0)
                val score4 = sharedPreferences.getInt("score4", 0)
                val score5 = sharedPreferences.getInt("score5", 0)

                val scores = mutableListOf<Int>(score1, score2, score3, score4, score5, score)
                scores.sortDescending()

                with(sharedPreferences.edit()){
                    putInt("score1", scores[0])
                    putInt("score2", scores[1])
                    putInt("score3", scores[2])
                    putInt("score4", scores[3])
                    putInt("score5", scores[4])
                    apply()
                }

            }
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        CustomDialogGenerator.createCustomDialog(this, "Czy chcesz wyjść z gry?", "Tak", "Nie"){
            gameViewModel.setClosedGame(true)
            super.onBackPressed()
        }
    }

    private fun createLayouts() {

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - 150
        val height = displayMetrics.heightPixels - 150

        for (i in 0..9) {
            val tableRow = TableRow(this).apply {
                id = i
            }
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for (j in 0..9) {

                Log.d(TAG, "$width x $height")
                val imageView = ImageView(this).apply {
                    id = j
                    adjustViewBounds = true
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    layoutParams = TableRow.LayoutParams(
                        min(height, width) / 10,
                        min(height, width) / 10
                    )
                    setBackgroundResource(R.drawable.image_border)
                }

                imageView.setOnClickListener {
                    val moveOutput = game.currentGameboard.move(i, j)
                    Log.d(TAG, moveOutput.toString())
                    handleOutput(moveOutput, imageView)
                }
                layoutBoard[i].add(imageView)
                tableRow.addView(imageView)

            }
            tlGameboard.addView(
                tableRow,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }

    }

    private fun handleOutput(moveOutput: MoveOutput, imageView: ImageView){
        when (moveOutput) {
            MoveOutput.CROSS_MOVED -> {
                imageView.setImageResource(R.drawable.cross)}
            MoveOutput.CIRCLE_MOVED -> {
                imageView.setImageResource(R.drawable.circle)
                if(singlePlayer){
                    do {
                    val x = Random.nextInt(0, game.currentGameboard.BOARD_SIZE - 1)
                    val y = Random.nextInt(0, game.currentGameboard.BOARD_SIZE - 1)
                        val moveOut = game.currentGameboard.move(x, y)
                        Log.d(TAG,"Bot moved: $moveOut")
                        if(moveOut != MoveOutput.DISALLOWED_HERE)
                            handleOutput(moveOut, layoutBoard[x][y])
                    }while (moveOut == MoveOutput.DISALLOWED_HERE)
                }

            }
            MoveOutput.CROSS_WIN -> {
                imageView.setImageResource(R.drawable.cross)
                Toast.makeText(this, "Krzyżyk wygrywa", Toast.LENGTH_SHORT).show()
                game.playerTwoWon()
                startNewGame()
            }
            MoveOutput.CIRCLE_WIN -> {
                imageView.setImageResource(R.drawable.circle)
                Toast.makeText(this, "Kółko wygrywa", Toast.LENGTH_SHORT).show()
                game.playerOneWon()
                startNewGame()
            }
            MoveOutput.DRAW -> {
                imageView.setBackgroundColor(R.drawable.cross)
                Toast.makeText(this, "Remis", Toast.LENGTH_SHORT).show()
                game.draw()
                startNewGame()
            }
            else -> {

            }
        }
    }

    private fun startNewGame(){
        game.start()
        updateLayout()
    }

    private fun updateLayout() {
        for (i in 0 until layoutBoard.size) {
            for (j in 0 until layoutBoard[i].size){
                when(game.currentGameboard.board[i][j]){
                    Sign.CROSS -> layoutBoard[i][j].setImageResource(R.drawable.cross)
                    Sign.CIRCLE -> layoutBoard[i][j].setImageResource(R.drawable.circle)
                    Sign.NOTHING -> layoutBoard[i][j].setImageDrawable(null)
                }
            }
        }
    }
}
