package com.adeleke.samad.wordsearch.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.gridlayout.widget.GridLayout

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

    private val TAG = this.javaClass.simpleName
    private val myLinePaint = Paint()
    private val myLinePath = Path()
    private val lineCoordinates = Array<Float>(4) { 0F }

    init {
        setUpLinePaint()
    }

    private fun setUpLinePaint() {
        myLinePaint.color = (Color.BLUE)
        myLinePaint.style = (Paint.Style.STROKE)
        myLinePaint.strokeWidth = 5F
        myLinePaint.strokeCap = Paint.Cap.ROUND
        myLinePaint.isAntiAlias = true

    }


    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return true
    }


    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawPath(myLinePath, myLinePaint)
        super.onDraw(canvas)
//        canvas!!.drawPath(myLinePath, myLinePaint)

    }


}