package com.adeleke.samad.wordsearch

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.adeleke.samad.wordsearch.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
//    private lateinit var board: Board
//    private lateinit var boardView: BoardView
//    private lateinit var allWordsGridView: GridView
//    private lateinit var currentWordTV: TextView
//    private lateinit var allWords: MutableList<String>
//    private var currentWord: String = ""
//    private var touchedLetterViews = mutableListOf<BoardLetterView>()


    private lateinit var binding: ActivityMainBinding
    private val viewModel: GameViewModel by viewModels()
    private lateinit var boardContainer: FrameLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        viewModel.boardView.observe(this, Observer { view ->
            board_container.addView(view)
        })
        viewModel.allWorldViewdapter.observe(this, Observer { adapter ->
            gv_all_words.adapter = adapter
        })


//        boardContainer = findViewById(R.id.board_container)
//        board = Board.makeDefaultGameBoard()
//        boardView = BoardViewMaker.makeBoardView(this, board)
//        currentWordTV = findViewById(R.id.tv_current_word)
//        insertBoardIntoContainer()
//
//        allWordsGridView = findViewById(R.id.gv_all_words)
//        loadAllHiddenWords()
//
//        boardView.setOnTouchListener(object : View.OnTouchListener {
//            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//                when (event!!.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        getLetterViewAtPoint(event.x, event.y)
//                        updateCurrentWordTv()
//                    }
//                    MotionEvent.ACTION_MOVE -> {
//                        getLetterViewAtPoint(event.x, event.y)
//                        updateCurrentWordTv()
//                    }
//
//                    MotionEvent.ACTION_UP -> {
//                        getLetterViewAtPoint(event.x, event.y)
//                        checkIfWordIsCorrect()
//                        updateCurrentWordTv()
//                    }
//                }
//                return true
//            }
//
//        })
//
//        updateCurrentWordTv()
//
//        restart_button.setOnClickListener { v ->
//            strikeOutWordOnBoard("sth")
//        }
    }


//    private fun getLetterViewAtPoint(x: Float, y: Float) {
//        for (index in 0..boardView.childCount) {
//            val childView = boardView.getChildAt(index);
//            val bounds = Rect();
//
//            if (childView != null) {
//                childView.getHitRect(bounds);
//                if (bounds.contains(x.toInt(), y.toInt())) {
//                    if (childView is BoardLetterView) {
//                        addToTouchedLetterViews(childView)
//                    }
//                }
//            }
//        }
//        getCurrentWordFromTouchedLetters()
//    }
//
//    private fun getCurrentWordFromTouchedLetters() {
//        val sb = StringBuilder()
//        for (view in touchedLetterViews) {
//            sb.append(view.text)
//        }
//        currentWord = sb.toString()
//    }
//
//    private fun checkIfWordIsCorrect() {
//        if (currentWord in allWords) {
//            strikeOutFoundWord(currentWord)
//            strikeOutWordOnBoard(currentWord)
//        }
//        currentWord = ""
//        touchedLetterViews.clear()
//    }
//
//    private fun addToTouchedLetterViews(view: BoardLetterView) {
//        if (touchedLetterViews.isEmpty()) {
//            touchedLetterViews.add(view)
//        } else if (view.id != touchedLetterViews[touchedLetterViews.size - 1].id) {
//            touchedLetterViews.add(view)
//        }
//    }
//
//    private fun insertBoardIntoContainer() {
//        val layoutParams = FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        layoutParams.marginEnd = 10
//        layoutParams.gravity = Gravity.CENTER
//        boardView.layoutParams = layoutParams
//        boardContainer.addView(boardView)
//        allWords = board.getWords()
//    }
//
//    private fun loadAllHiddenWords() {
//        val gridViewAdapter =
//            MyGridViewAdapter(this, allWords)
//        allWordsGridView.adapter = gridViewAdapter
//    }
//
//    private fun strikeOutFoundWord(word: String) {
//        val index = allWords.indexOf(word)
//        val textView = allWordsGridView.getChildAt(index) as TextView
//        textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
//    }
//
//    private fun strikeOutWordOnBoard(word: String) {
//        val myView = boardView.findViewWithTag<BoardLetterView>("0,1")
//        3
//    }
//
//    private fun updateCurrentWordTv() {
//        currentWordTV.setText(currentWord)
}


