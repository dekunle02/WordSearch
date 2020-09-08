package com.adeleke.samad.wordsearch

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.adeleke.samad.wordsearch.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GameViewModel by viewModels()

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

        viewModel.hasWon.observe(this, Observer { w->
            if(w) {
                showRestartPopup(restart_button)
            }
        })

        viewModel.wordFromMove.observe(this, Observer { word->
            tv_currentWord.text = (word)
            println("word has moved-> ${tv_currentWord.text}")
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
        textView.setTextColor(this.resources.getColor(R.color.colorSkyBlue))
    }

    private fun showRestartPopup(view: View) {
        val builder =
            AlertDialog.Builder(this)
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(view.context).inflate(R.layout.popup_restart, viewGroup, false)
        builder.setView(dialogView)
        val alertDialog = builder.create()
        alertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()

        val btnConfirmRestart: MaterialButton = dialogView.findViewById(R.id.btn_confirm_restart)
        btnConfirmRestart.setOnClickListener {
            viewModel.resetGame()
            alertDialog.dismiss()
        }
        val btnCancelSubmit = dialogView.findViewById<ImageButton>(R.id.btn_cancel)
        btnCancelSubmit.setOnClickListener { alertDialog.dismiss() }
    }

}


