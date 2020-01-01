package com.materiotech.rajkumarnalluchamy.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.materiotech.rajkumarnalluchamy.R

class TicTacToeAdapter(context: Context): BaseAdapter(){

    private val mInflator: LayoutInflater
    val size: Int = 9;

    init {
        mInflator = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.item_row_layout, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

//        vh.label.text = sList[position]
        return view


    }

    override fun getItem(position: Int): Any {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return position
    }

    override fun getItemId(position: Int): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return position.toLong()
    }

    override fun getCount(): Int {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return size
    }

    private class ListRowHolder(row: View?) {
        public val label: TextView

        init {
            this.label = row?.findViewById(R.id.textView) as TextView
        }
    }
}