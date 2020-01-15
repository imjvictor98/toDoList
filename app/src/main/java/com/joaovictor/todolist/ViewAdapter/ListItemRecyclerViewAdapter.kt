package com.joaovictor.todolist.ViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.R
import com.joaovictor.todolist.TaskList
import com.joaovictor.todolist.ViewHolder.ListItemViewHolder

class ListItemRecyclerViewAdapter(var list: TaskList): RecyclerView.Adapter<ListItemViewHolder>() {

    //@Override: Recycler View Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        /* RecyclerView has to know what Layout to use as the View Holder*/
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view_holder, parent, false)

        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.tasks.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.taskTextView.text = list.tasks[position]
    }
}