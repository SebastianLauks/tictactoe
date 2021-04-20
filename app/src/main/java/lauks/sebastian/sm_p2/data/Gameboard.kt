package lauks.sebastian.sm_p2.data

import android.util.Log
import android.widget.Toast
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.log

class Gameboard() {

    val TAG ="Gameboard"
    val BOARD_SIZE = 10
    val SIGNS_TO_WIN = 5
    val board: MutableList<MutableList<Sign>> = mutableListOf()

    init {
        for (i in 0 until BOARD_SIZE) {
            board.add(
                i,
                mutableListOf(
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING,
                    Sign.NOTHING
                )
            )
        }
    }

    fun move(x: Int, y: Int): MoveOutput {
        val sign = if(countSigns(Sign.CIRCLE) > countSigns(Sign.CROSS)) Sign.CROSS else Sign.CIRCLE
        val oppositeSign = if (sign == Sign.CROSS) Sign.CIRCLE else Sign.CROSS

        if (countSigns(sign) <= countSigns(oppositeSign)) {
            if(board[x][y] == Sign.NOTHING){
                board[x][y] = sign
                if(checkIfWon(sign, x, y)){
                    return if(sign == Sign.CIRCLE) MoveOutput.CIRCLE_WIN else MoveOutput.CROSS_WIN
                }else{
                    if (countSigns(Sign.NOTHING) == 0) {
                        return MoveOutput.DRAW
                    }
                    return if(sign == Sign.CROSS) MoveOutput.CROSS_MOVED else MoveOutput.CIRCLE_MOVED
                }
            }else{
                return MoveOutput.DISALLOWED_HERE
            }
        } else {
            return MoveOutput.WRONG_PLAYER
        }
    }

    private fun checkIfWon(checkingSign: Sign, lastX: Int, lastY: Int): Boolean{

        val oppositeSign = if (checkingSign == Sign.CROSS) Sign.CIRCLE else Sign.CROSS

        Log.d(TAG,"Clicked: $lastX, $lastY")

        var counter = 0
        for(i in lastX + 1 until BOARD_SIZE){
            if(board[i][lastY] != checkingSign) break
            counter++
        }


        for(i in (lastX - 1).downTo(0)){
            if(board[i][lastY] != checkingSign) break
            counter++
        }

        if(counter >= SIGNS_TO_WIN - 1) return true

        counter = 0

        for(i in lastY + 1 until BOARD_SIZE){
            if(board[lastX][i]!= checkingSign) break
            counter++
        }


        for(i in (lastY - 1).downTo(0)){
            if(board[lastX][i] != checkingSign) break
            counter++
        }


        if(counter >= SIGNS_TO_WIN - 1) return true


        counter = 0

        val higher = max(lastX, lastY)
        val difference = max(lastX, lastY) - min(lastX, lastY)

        val xIsHigher = max(lastX, lastY) == lastX


        for (i in (min(lastX, lastY)-1).downTo(0)){
            if(xIsHigher){
               if(board[i+difference][i] != checkingSign) break
                counter++
            }else{
                if(board[i][i+difference] != checkingSign) break
                counter++
            }
        }


        for (i in (min(lastX, lastY)+1) until BOARD_SIZE - difference){
            if(xIsHigher){
                if(board[i+difference][i] != checkingSign) break
                counter++
            }else{
                if(board[i][i+difference] != checkingSign) break
                counter++
            }
        }

        if (counter >= SIGNS_TO_WIN - 1) return true
        counter = 0

        var forCounter = 0
        for (i in (min(lastX, lastY)-1).downTo(0)){
//            if(xIsHigher){
//                if(board[BOARD_SIZE - 1 - i][i+difference] != checkingSign) break
//                counter++
//            }else{
//                if(board[i+difference][BOARD_SIZE - 1 - i] != checkingSign) break
//                counter++
//            }
            forCounter++
            if(i+difference + 2 * forCounter >= BOARD_SIZE || i+difference + 2 * forCounter < 0) break
            if(xIsHigher){
                if(board[i+difference + 2 * forCounter][i] != checkingSign) break
                counter++
            }else{
                if(board[i][i+difference + 2 * forCounter] != checkingSign) break
                counter++
            }
        }


        forCounter = 0
        for (i in (min(lastX, lastY)+1) until BOARD_SIZE){
//            if(xIsHigher){
//                if(board[BOARD_SIZE - 1 - i][i+difference] != checkingSign) break
//                counter++
//            }else{
//                if(board[i+difference][BOARD_SIZE - 1 - i] != checkingSign) break
//                counter++
//            }

            forCounter++
            if(i+difference - 2 * forCounter >= BOARD_SIZE || i+difference - 2 * forCounter < 0) break
            if(xIsHigher){
                if(board[i+difference - 2 * forCounter][i] != checkingSign) break
                counter++
            }else{
                if(board[i][i+difference - 2 * forCounter] != checkingSign) break
                counter++
            }
        }


        if (counter >= SIGNS_TO_WIN - 1) return true

        return false

    }

    private fun countSigns(countingSign: Sign): Int {
        var counter = 0
        board.forEach {
            it.forEach { sign ->
                if (sign == countingSign) counter++

            }
        }
        return counter
    }

}