package com.adeleke.samad.wordsearch.util

import android.content.Context
import android.util.Log
import android.view.Gravity
import androidx.core.view.ViewCompat
import androidx.gridlayout.widget.GridLayout
import com.adeleke.samad.wordsearch.views.BoardView
import com.adeleke.samad.wordsearch.views.BoardLetterView

class BoardViewMaker(private val context: Context, board: Board ) {
    private val TAG = this.javaClass.simpleName

    private val mBoard = board
    private val boardArray = mBoard.getBoardArray()
    private val dimension = mBoard.getBoardDimension()
    private lateinit var boardView: BoardView


    companion object{
        fun makeBoardView(context: Context, board: Board): BoardView{
            val boardViewMaker = BoardViewMaker(context, board)
            return boardViewMaker.boardView
        }
    }


    init {
        setUpBoardView()
        populateBoard()
    }


    private fun setUpBoardView() {
        boardView = BoardView(context)
        boardView.rowCount = dimension
        boardView.columnCount = dimension
    }

    private fun createItemView(letter: String, rowIndex: Int, columnIndex: Int): BoardLetterView {
        val itemView = BoardLetterView(context)
        itemView.width = 100
        itemView.height = 100
        itemView.id = ViewCompat.generateViewId()
        itemView.text = letter
        itemView.textSize = 20F
        itemView.gravity = Gravity.CENTER
        itemView.tag = "$rowIndex,$columnIndex"

        return itemView
    }


    private fun populateBoard() {
        boardArray.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, letter ->
                val gridViewItem = createItemView(letter, rowIndex, columnIndex)
                boardView.addView(
                    gridViewItem, GridLayout.LayoutParams(
                        GridLayout.spec(rowIndex, GridLayout.CENTER),
                        GridLayout.spec(columnIndex, GridLayout.CENTER)
                    )
                )
            }
        }
    }

}

