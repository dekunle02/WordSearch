package com.adeleke.samad.wordsearch

import android.graphics.Paint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
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
            board_container.removeAllViewsInLayout()
            board_container.addView(view)
        })
        viewModel.allWorldViewdapter.observe(this, Observer { adapter ->
            gv_all_words.adapter = adapter
        })

        viewModel.correctAnswer.observe(this, Observer { correctAnswer->
            strikeOutFoundWord(correctAnswer)
        })

        restart_button.setOnClickListener {view ->
            viewModel.resetGame()
        }

        hint_button.setOnClickListener { view->
            viewModel.showHint()
        }
        
    }

    private fun strikeOutFoundWord(word: String) {
        val index = viewModel.allWords.indexOf(word)
        val textView = gv_all_words.getChildAt(index) as TextView
        textView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

}


