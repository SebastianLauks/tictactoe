package lauks.sebastian.sm_p2.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_scoreboard.*
import lauks.sebastian.sm_p2.R
import lauks.sebastian.sm_p2.utils.CustomDialogGenerator
import lauks.sebastian.sm_p2.viewmodel.OnlineGameViewModel

class ScoreboardActivity : AppCompatActivity() {

    val TAG = "ScoreboardActivity"
    private lateinit var onlineGameViewModel: OnlineGameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)

        onlineGameViewModel = ViewModelProvider(this).get(OnlineGameViewModel::class.java)

        updateScores()
        updateScoresOnline()

//        btReset.setOnClickListener {
//            CustomDialogGenerator.createCustomDialog(this, "Czy chcesz zresetować tablicę wyników?", "Tak", " Nie"){
//                val sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)
//                if(sharedPreferences != null){
//
//                    with(sharedPreferences.edit()){
//                        putInt("score1", 0)
//                        putInt("score2", 0)
//                        putInt("score3", 0)
//                        putInt("score4", 0)
//                        putInt("score5", 0)
//                        apply()
//                    }
//                    updateScores()
//                }
//            }
//        }

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

    private fun updateScoresOnline() {
        onlineGameViewModel.getBestFive()
        onlineGameViewModel.bestFiveLD().observe(this, Observer {

            if(it != null){
                tvScore1.text = if(it.size >= 1) it[0].toString() else "0"
                tvScore2.text = if(it.size >= 2) it[1].toString() else "0"
                tvScore3.text = if(it.size >= 3) it[2].toString() else "0"
                tvScore4.text = if(it.size >= 4) it[3].toString() else "0"
                tvScore5.text = if(it.size >= 5) it[4].toString() else "0"
            }



        })



    }
}
