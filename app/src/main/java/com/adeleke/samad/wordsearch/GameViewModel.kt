package com.adeleke.samad.wordsearch

import android.app.Application
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adeleke.samad.wordsearch.helper.MyGridViewAdapter
import com.adeleke.samad.wordsearch.util.Board
import com.adeleke.samad.wordsearch.util.BoardViewMaker
import com.adeleke.samad.wordsearch.views.BoardLetterView
import com.adeleke.samad.wordsearch.views.BoardView

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = javaClass.simpleName
    private val context = application.applicationContext

    private var board: Board = Board.makeDefaultGameBoard()
    private lateinit var allWords: MutableList<String>

    private var foundWords = mutableListOf<List<String>>()
    private var currentWord: String = ""
    private var currentDirection = BoardLetterView.LineDirection.OFF

    private lateinit var startView: BoardLetterView
    private lateinit var endView: BoardLetterView


    private var _boardView = MutableLiveData<BoardView>()
    val boardView: LiveData<BoardView>
        get() = _boardView

    private var _allWordsAdapter = MutableLiveData<MyGridViewAdapter>()
    val allWorldViewdapter: LiveData<MyGridViewAdapter>
        get() = _allWordsAdapter

    init {
        _boardView.value = BoardViewMaker.makeBoardView(context, board)
        allWords = board.getWords()
        _allWordsAdapter.value = MyGridViewAdapter(context, allWords)

        boardView.value!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                 startView = getLetterViewAtPoint(event!!.x, event!!.y)!!
                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                    }
                    MotionEvent.ACTION_MOVE -> {
                    }
                    MotionEvent.ACTION_UP -> {
                        endView = getLetterViewAtPoint(event.x, event.y)!!
                        doTheThing()
                    }

                }
                return true
            }

        })
    }

    private fun doTheThing() {
        println("x-> ${startView!!.tag}")
        println("y-> ${endView!!.tag}")
        val coordinates = board.getWordFromEndPoints(startView!!.tag as String, endView!!.tag as String)
        getWordSwiped(coordinates)
        println("currentword -> $currentWord")
    }

    private fun getLetterViewAtPoint(x: Float, y: Float): BoardLetterView? {
        for (index in 0.._boardView.value!!.childCount) {
            val childView = _boardView.value!!.getChildAt(index);
            val bounds = Rect();

            if (childView != null) {
                childView.getHitRect(bounds);
                if (bounds.contains(x.toInt(), y.toInt())) {
                    if (childView is BoardLetterView) {
                        return childView
                    }
                }
            }
        }
        return null
    }

    private fun getWordSwiped(wordCoordinates: MutableList<IntArray>) {
        val sb = StringBuilder()
        val boardArray = board.getBoardArray()

        for (coordinate in wordCoordinates) {
            Log.d(TAG, "Current coordinates -> ${coordinate.contentToString()}")
            sb.append(boardArray[coordinate[0]][coordinate[1]])
        }
        currentWord = sb.toString()
    }

    private fun strikeThroughViews(
        views: Array<BoardLetterView>,
        lineDirection: BoardLetterView.LineDirection
    ) {
        Log.d(TAG, "StrikeThroughViews Called")
        for (view in views) {
            view.setLineDirection(lineDirection)
        }
    }


}