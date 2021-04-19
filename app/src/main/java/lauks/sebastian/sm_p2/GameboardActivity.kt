package lauks.sebastian.sm_p2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_gameboard.*
import lauks.sebastian.sm_p2.data.Gameboard
import lauks.sebastian.sm_p2.data.MoveOutput
import lauks.sebastian.sm_p2.utils.CustomDialogGenerator
import java.lang.Integer.min

class GameboardActivity : AppCompatActivity() {

    val TAG ="GameboardActivity"
    val gameboard = Gameboard()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameboard)


        createLayouts()
    }

    private fun createLayouts(){
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels - 150
        val height = displayMetrics.heightPixels - 150

        for(i in 0..9){
            val tableRow = TableRow(this).apply {
                id = i
            }
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for(j in 0..9){

                Log.d(TAG,"$width x $height")
                val imageView = ImageView(this).apply {
                    id = j
                    adjustViewBounds = true
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    layoutParams = TableRow.LayoutParams(
                        min(height,width)/10,
                        min(height,width)/10
                    )
                    setBackgroundResource(R.drawable.image_border)
                }

                imageView.setOnClickListener {
                   val moveOutput =  gameboard.move(i,j)
                    Log.d(TAG,moveOutput.toString())
                    when(moveOutput){
                        MoveOutput.CROSS_MOVED -> imageView.setImageResource(R.drawable.cross)
                        MoveOutput.CIRCLE_MOVED -> imageView.setBackgroundColor(R.drawable.circle)
                        MoveOutput.CROSS_WIN -> {
                            imageView.setImageResource(R.drawable.cross)
                            Toast.makeText(this, "Krzyżyk wygrywa", Toast.LENGTH_SHORT).show()
                        }
                        MoveOutput.CIRCLE_WIN -> {imageView.setBackgroundColor(R.drawable.circle)
                            Toast.makeText(this, "Kółko wygrywa", Toast.LENGTH_SHORT).show()}
                        MoveOutput.DRAW -> imageView.setBackgroundColor(R.drawable.circle)
                    }


                }

                tableRow.addView(imageView)

            }
            tlGameboard.addView(tableRow, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        }

    }
}
