package com.adeleke.samad.wordsearch.helper

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.TextView
import com.adeleke.samad.wordsearch.R

class MyGridViewAdapter(context: Context, wordsList: MutableList<String>) : BaseAdapter() {
    private val data: MutableList<String> = wordsList
    private val mContext = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val wordTextView: TextView = TextView(mContext)
        wordTextView.layoutParams = AbsListView.LayoutParams(300, 50)
        wordTextView.gravity = TextView.TEXT_ALIGNMENT_CENTER
        wordTextView.setPadding(4, 4, 4, 4)
        wordTextView.text = (data[position])
        wordTextView.setTextColor(mContext.resources.getColor(R.color.colorCream))
        return wordTextView
}

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }
}