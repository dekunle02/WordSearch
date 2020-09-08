package com.adeleke.samad.wordsearch

import android.app.Application
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adeleke.samad.wordsearch.helper.MyGridViewAdapter
import com.adeleke.samad.wordsearch.util.Board
import com.adeleke.samad.wordsearch.util.BoardViewMaker
import com.adeleke.samad.wordsearch.views.BoardLetterView
import com.adeleke.samad.wordsearch.views.BoardView
import java.util.*

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = javaClass.simpleName
    private val context = application.applicationContext

    private lateinit var board: Board
    lateinit var allWords: MutableList<String>

    private var foundWords = mutableListOf<List<String>>()
    private var currentWord: String = ""
    private var currentDirection = BoardLetterView.LineDirection.OFF

    private var _correctAnswer= MutableLiveData<String>()
    val correctAnswer: LiveData<String>
        get() = _correctAnswer


    private lateinit var startView: BoardLetterView
    private lateinit var endView: BoardLetterView


    private var _boardView = MutableLiveData<BoardView>()
    val boardView: LiveData<BoardView>
        get() = _boardView

    private var _allWordsAdapter = MutableLiveData<MyGridViewAdapter>()
    val allWorldViewdapter: LiveData<MyGridViewAdapter>
        get() = _allWordsAdapter

    init {
        resetGame()
    }

    private fun getLetterViewAtPoint(x: Float, y: Float): BoardLetterView? {
        for (index in 0.._boardView.value!!.childCount) {
            val childView = _boardView.value!!.getChildAt(index);
            val bounds = Rect();

            if (childView != null) {
                childView.getHitRect(bounds);
                if (bounds.contains(x.toInt(), y.toInt())) {
                    if (childView is BoardLetterView) {
                        println("childTag ->" +childView.tag as String)
                        return childView
                    }
                }
            }
        }
        return null
    }

    private fun getWordSwiped(wordCoordinates: MutableList<IntArray>): String {
        val sb = StringBuilder()
        val boardArray = board.getBoardArray()

        for (coordinate in wordCoordinates) {
            sb.append(boardArray[coordinate[0]][coordinate[1]])
        }
        return sb.toString()
    }




    fun resetGame() {
        board = Board.makeDefaultGameBoard()
        val boardView = BoardViewMaker.makeBoardView(context, board)
        allWords = board.getWords()
        _allWordsAdapter.value = MyGridViewAdapter(context, allWords)

        fun strikeThroughFoundWords(
            finalLineDirection: BoardLetterView.LineDirection,
            finalCoordinates: MutableList<IntArray>
        ) {
            for (coordinate in finalCoordinates) {
                val boardItemView: BoardLetterView = boardView.findViewWithTag("${coordinate[0]},${coordinate[1]}")
                boardItemView.setLineDirection(finalLineDirection)

            }
        }

        var startTag: String = ""
        var endTag: String = ""
        boardView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                when (event!!.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startTag = getLetterViewAtPoint(event!!.x, event!!.y)!!.tag as String
                        println("startTag-> $startTag")
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val moveTag = getLetterViewAtPoint(event.x, event.y)!!.tag as String
                        val (movingDirection, movingCoordinates) = board.getWordFromEndPoints(startTag, moveTag)
                        currentDirection = movingDirection
                    }
                    MotionEvent.ACTION_UP -> {
                        endTag = getLetterViewAtPoint(event.x, event.y)!!.tag as String
                        val (finalLineDirection, finalCoordinates) = board.getWordFromEndPoints(startTag, endTag)
                        currentWord = (getWordSwiped(finalCoordinates))
                        currentDirection = finalLineDirection
                        val reversedWord = currentWord.reversed()
                        println("reversed-> $reversedWord")
                        if (reversedWord in allWords){
                            currentWord = reversedWord
                        }
                        if (currentWord in allWords) {
                            println("CorrectWord -> $currentWord")
                            strikeThroughFoundWords(finalLineDirection, finalCoordinates)
                            _correctAnswer.value = currentWord
                            currentWord = ""
                        }
                        println(currentWord)
                    }
                }

                return true
            }
        })
        _boardView.value = boardView
    }



    fun showHint() {
        val correctCoordinates = board.getCorrectCoordinates()
        val random =Random()
        val randomWord = allWords.random()
        val randomCoordinates = correctCoordinates[randomWord]

        val message = if (random.nextInt(2) == 1) randomCoordinates!![0][0] else randomCoordinates!![0][1]
        Toast.makeText(context, "Have you tried checking around row $message or was it the column", Toast.LENGTH_SHORT).show()
    }

}