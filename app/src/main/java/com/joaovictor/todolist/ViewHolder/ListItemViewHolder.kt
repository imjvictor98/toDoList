package com.joaovictor.todolist.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.R

class ListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val taskTextView = itemView.findViewById(R.id.textview_task) as TextView


}