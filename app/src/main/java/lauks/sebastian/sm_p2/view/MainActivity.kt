package lauks.sebastian.sm_p2.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import lauks.sebastian.sm_p2.R
import lauks.sebastian.sm_p2.utils.CustomDialogGenerator
import lauks.sebastian.sm_p2.viewmodel.GameViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)


        btSignlePlayer.setOnClickListener {
            val intent = Intent(this, GameboardActivity::class.java)
            intent.putExtra("singlePlayer", true)
            startActivity(intent)
        }

        btMultiPlayer.setOnClickListener {
            val intent = Intent(this, GameboardActivity::class.java)
            intent.putExtra("singlePlayer", false)
            startActivity(intent)
        }

        btScoreBoard.setOnClickListener {
            val intent = Intent(this, ScoreboardActivity::class.java)
            startActivity(intent)
        }

        btFinish.setOnClickListener {
            CustomDialogGenerator.createCustomDialog(this, "Czy chcesz wyjść z aplikacji?", "Tak", "Nie"){
                finish()
            }

        }
    }
}
