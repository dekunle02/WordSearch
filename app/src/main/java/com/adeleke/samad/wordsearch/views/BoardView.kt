package com.adeleke.samad.wordsearch.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.gridlayout.widget.GridLayout
import com.adeleke.samad.wordsearch.helper.getRandomColor

class BoardView : GridLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    init {
    }



    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

}