package lauks.sebastian.sm_p2.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class CustomDialogGenerator {
    companion object{
        fun createCustomDialog(context: Context, message: String, yes: String, no: String, yesFunction: () -> Unit) {
            val builder = AlertDialog.Builder(context)

            // Set the alert dialog title
//            builder.setTitle("Usun wszystkie przedmioty")

            // Display a message on alert dialog
            builder.setMessage(message)

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton(yes) { _, _ ->
                yesFunction()
            }


            // Display a negative button on alert dialog
            builder.setNegativeButton(no) { _, _ ->
            }

            builder.create().show()
        }
    }
}
