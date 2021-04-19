package lauks.sebastian.sm_p2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_gameboard.*
import lauks.sebastian.sm_p2.data.Game
import lauks.sebastian.sm_p2.data.Gameboard
import lauks.sebastian.sm_p2.data.MoveOutput
import lauks.sebastian.sm_p2.data.Sign
import lauks.sebastian.sm_p2.utils.CustomDialogGenerator
import lauks.sebastian.sm_p2.viewmodel.GameViewModel
import java.lang.Integer.min

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameboard)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        game = gameViewModel.getGame()
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
        super.onStop()
        gameViewModel.saveGame(game)
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
                    when (moveOutput) {
                        MoveOutput.CROSS_MOVED -> imageView.setImageResource(R.drawable.cross)
                        MoveOutput.CIRCLE_MOVED -> imageView.setImageResource(R.drawable.circle)
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
                        MoveOutput.DRAW -> imageView.setBackgroundColor(R.drawable.circle)
                    }


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
