package lauks.sebastian.sm_p2.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_scoreboard.*
import lauks.sebastian.sm_p2.R
import lauks.sebastian.sm_p2.utils.CustomDialogGenerator

class ScoreboardActivity : AppCompatActivity() {

    val TAG = "ScoreboardActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        updateScores()

        btReset.setOnClickListener {
            CustomDialogGenerator.createCustomDialog(this, "Czy chcesz zresetować tablicę wyników?", "Tak", " Nie"){
                val sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)
                if(sharedPreferences != null){

                    with(sharedPreferences.edit()){
                        putInt("score1", 0)
                        putInt("score2", 0)
                        putInt("score3", 0)
                        putInt("score4", 0)
                        putInt("score5", 0)
                        apply()
                    }
                    updateScores()
                }
            }
        }

    }

    private fun updateScores() {
        val sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            val score1 = sharedPreferences.getInt("score1", 0)
            val score2 = sharedPreferences.getInt("score2", 0)
            val score3 = sharedPreferences.getInt("score3", 0)
            val score4 = sharedPreferences.getInt("score4", 0)
            val score5 = sharedPreferences.getInt("score5", 0)


            tvScore1.text = score1.toString()
            tvScore2.text = score2.toString()
            tvScore3.text = score3.toString()
            tvScore4.text = score4.toString()
            tvScore5.text = score5.toString()



        }
    }
}
