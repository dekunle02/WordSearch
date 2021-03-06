package com.adeleke.samad.wordsearch.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet


class BoardLetterView : androidx.appcompat.widget.AppCompatTextView {
    private val TAG = this.javaClass.simpleName
    private val myLinePaint = Paint()
    enum class LineDirection { OFF, HOR, VERT, LDIAG, RDIAG }

    var currentLineDirection: LineDirection = LineDirection.OFF

    private fun setUpLinePaint() {
        myLinePaint.style = (Paint.Style.STROKE)
        myLinePaint.strokeWidth = 60F
        myLinePaint.strokeCap = Paint.Cap.SQUARE
        myLinePaint.isAntiAlias = true
    }


    init {
        setUpLinePaint()
    }

    fun setLineDirection(lineDirection: LineDirection, color: Int) {
        myLinePaint.color = color
        currentLineDirection = lineDirection
        postInvalidate()
    }


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


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            heightMeasureSpec,
            heightMeasureSpec
        )
    }

    override fun onDraw(canvas: Canvas?) {
        when (currentLineDirection) {
            LineDirection.HOR -> canvas!!.drawLine(
                0F,
                measuredHeight.toFloat() / 2,
                measuredWidth.toFloat(),
                measuredHeight.toFloat() / 2,
                myLinePaint
            )
            LineDirection.VERT -> canvas!!.drawLine(
                measuredWidth.toFloat() / 2,
                0F,
                measuredWidth.toFloat() / 2,
                measuredHeight.toFloat(),
                myLinePaint
            )
            LineDirection.LDIAG -> canvas!!.drawLine(
                0F,
                0F,
                measuredWidth.toFloat(),
                measuredHeight.toFloat(),
                myLinePaint
            )
            LineDirection.RDIAG -> canvas!!.drawLine(
                measuredWidth.toFloat(),
                0F,
                0F,
                measuredHeight.toFloat(),
                myLinePaint
            )
        }
        super.onDraw(canvas)
    }

}

