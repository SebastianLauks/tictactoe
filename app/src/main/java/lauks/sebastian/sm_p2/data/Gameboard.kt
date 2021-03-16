package lauks.sebastian.sm_p2.data

import java.lang.Integer.max
import java.lang.Integer.min

class Gameboard {

    private val BOARD_SIZE = 10
    private val SIGNS_TO_WIN = 5
    private val board: MutableList<MutableList<Sign>> = mutableListOf()

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

    fun move(x: Int, y: Int) {

        val sign = if(countSigns(Sign.CIRCLE) > countSigns(Sign.CROSS)) Sign.CROSS else Sign.CIRCLE
        val oppositeSign = if (sign == Sign.CROSS) Sign.CIRCLE else Sign.CROSS

        if (countSigns(sign) <= countSigns(oppositeSign)) {
            if(board[x][y] == Sign.NOTHING){
                board[x][y] = sign
                if(checkIfWon(sign, x, y)){
                    //won
                }else{
                    if (countSigns(Sign.NOTHING) == 0) {
                        //draw
                    }
                }


            }else{
                //can't move here
            }
        } else {
            //illegal move
        }

    }

    private fun checkIfWon(checkingSign: Sign, lastX: Int, lastY: Int): Boolean{

        val oppositeSign = if (checkingSign == Sign.CROSS) Sign.CIRCLE else Sign.CROSS

        var counter = 0
        for(i in lastX + 1 until BOARD_SIZE){
            if(board[i][lastY] == oppositeSign) break
            counter++
        }

        for(i in (lastX - 1).downTo(0)){
            if(board[i][lastY] == oppositeSign) break
            counter++
        }

        if(counter >= SIGNS_TO_WIN) return true

        counter = 0

        for(i in lastY + 1 until BOARD_SIZE){
            if(board[lastX][i] == oppositeSign) break
            counter++
        }

        for(i in (lastY - 1).downTo(0)){
            if(board[lastX][i] == oppositeSign) break
            counter++
        }

        if(counter >= SIGNS_TO_WIN) return true

        counter = 0

        val higher = max(lastX, lastY)
        val difference = max(lastX, lastY) * min(lastX, lastY)

        val xIsHigher = max(lastX, lastY) == lastX


        for (i in (min(lastX, lastY)-1).downTo(0)){
            if(xIsHigher){
               if(board[i+difference][i] == oppositeSign) break
                counter++
            }else{
                if(board[i][i+difference] == oppositeSign) break
                counter++
            }
        }

        for (i in (min(lastX, lastY)+1) until BOARD_SIZE - difference){
            if(xIsHigher){
                if(board[i+difference][i] == oppositeSign) break
                counter++
            }else{
                if(board[i][i+difference] == oppositeSign) break
                counter++
            }
        }

        if (counter >= SIGNS_TO_WIN) return true
        counter = 0

        for (i in (min(lastX, lastY)-1).downTo(0)){
            if(xIsHigher){
                if(board[i+difference][BOARD_SIZE - i] == oppositeSign) break
                counter++
            }else{
                if(board[BOARD_SIZE - i][i+difference] == oppositeSign) break
                counter++
            }
        }

        for (i in (min(lastX, lastY)+1) until BOARD_SIZE - difference){
            if(xIsHigher){
                if(board[i+difference][BOARD_SIZE - i] == oppositeSign) break
                counter++
            }else{
                if(board[BOARD_SIZE - i][i+difference] == oppositeSign) break
                counter++
            }
        }

        if (counter >= SIGNS_TO_WIN) return true

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