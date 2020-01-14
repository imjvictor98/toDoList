package com.joaovictor.todolist.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.R

/*  HOLDER:   Visual containers for your item, think of them as cells in the table. */

class ListSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val listPosition = itemView.findViewById(R.id.itemNumber) as TextView
    val listTitle = itemView.findViewById(R.id.itemString) as TextView
}