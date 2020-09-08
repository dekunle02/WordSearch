package com.adeleke.samad.wordsearch.helper

import android.content.Context
import com.adeleke.samad.wordsearch.R

fun Context.getRandomColor(): Int {
   val colorList = arrayListOf(
       this.resources.getColor(R.color.transparentBlue),
       this.resources.getColor(R.color.transparentGreen),
       this.resources.getColor(R.color.transparentOrange),
       this.resources.getColor(R.color.transparentPink)
   )
    return colorList.random()
}


