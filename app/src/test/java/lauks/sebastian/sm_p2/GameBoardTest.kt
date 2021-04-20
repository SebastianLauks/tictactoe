package lauks.sebastian.sm_p2

import lauks.sebastian.sm_p2.data.Gameboard
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import lauks.sebastian.sm_p2.data.MoveOutput
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals


class GameBoardTest {

    @Test
    fun caseVerticalTest(){
        val gameboard = Gameboard()

        var output = gameboard.move(0,0)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,0)
        assertEquals(output, MoveOutput.DISALLOWED_HERE)

        output = gameboard.move(0,1)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(1,0)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,2)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(2,0)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(3,0)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.DISALLOWED_HERE)

        output = gameboard.move(0,4)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(4,0)
        assertEquals(output, MoveOutput.CIRCLE_WIN)
    }

    @Test
    fun caseHorizontalTest(){
        val gameboard = Gameboard()

        var output = gameboard.move(0,0)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(1,0)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(0,1)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(2,0)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(0,2)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(3,0)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(4,0)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(0,4)
        assertEquals(output, MoveOutput.CIRCLE_WIN)
    }

    @Test
    fun caseDiagonallyRightTest(){
        val gameboard = Gameboard()

        var output = gameboard.move(2,3)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,1)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(3,4)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,2)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(4,5)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(5,6)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,4)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(6,7)
        assertEquals(output, MoveOutput.CIRCLE_WIN)
    }



    @Test
    fun caseDiagonallyRightWithGapTest(){
        val gameboard = Gameboard()

        var output = gameboard.move(2,3)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,1)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(3,4)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,2)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(6,7)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(5,6)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,4)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(4,5)
        assertEquals(output, MoveOutput.CIRCLE_WIN)
    }


    @Test
    fun caseDiagonallyLeftTest(){
        val gameboard = Gameboard()

        var output = gameboard.move(2,8)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,1)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(3,7)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,2)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(4,6)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(5,5)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,4)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(6,4)
        assertEquals(output, MoveOutput.CIRCLE_WIN)
    }


    @Test
    fun crossWin(){
        val gameboard = Gameboard()

        var output = gameboard.move(2,3)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,1)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(3,4)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,2)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(6,7)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,3)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(5,6)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,4)
        assertEquals(output, MoveOutput.CROSS_MOVED)

        output = gameboard.move(4,6)
        assertEquals(output, MoveOutput.CIRCLE_MOVED)

        output = gameboard.move(0,5)
        assertEquals(output, MoveOutput.CROSS_WIN)
    }


    @Test
    fun caseDrawTest(){
        val gameboard = Gameboard()

        for(i in 0..3){
            for (j in 0 until  gameboard.BOARD_SIZE){
                val output = gameboard.move(i,j)
                assertNotEquals(MoveOutput.DRAW, output)
                assertNotEquals(MoveOutput.DISALLOWED_HERE, output)
            }
        }

        for(i in 4..7){
            for (j in (gameboard.BOARD_SIZE - 1).downTo(0)) {
                val output = gameboard.move(i,j)
                    assertNotEquals(MoveOutput.DRAW, output)
                    assertNotEquals(MoveOutput.DISALLOWED_HERE, output)

            }
        }

        for(i in 8..9){
            for (j in 0 until  gameboard.BOARD_SIZE) {
                val output = gameboard.move(i,j)
                if (i == gameboard.BOARD_SIZE - 1 && j == gameboard.BOARD_SIZE - 1) {
                    assertEquals(MoveOutput.DRAW, output)
                }else {
                    assertNotEquals(MoveOutput.DRAW, output)
                    assertNotEquals(MoveOutput.DISALLOWED_HERE, output)
                }
            }
        }

    }
}