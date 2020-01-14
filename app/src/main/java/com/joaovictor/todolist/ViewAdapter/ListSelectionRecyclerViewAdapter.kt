package com.joaovictor.todolist.ViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.ViewHolder.ListSelectionViewHolder
import com.joaovictor.todolist.R
import com.joaovictor.todolist.TaskList

/* ADAPTER:  Give your recycler view the data it wants to show*/

class ListSelectionRecyclerViewAdapter(
    val lists: ArrayList<TaskList>,
    val clickListener: ListSelectionRecyclerViewClickListener
):
        RecyclerView.Adapter<ListSelectionViewHolder>() {

    //Interface Definition
    interface ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: TaskList)
    }

    //Variables
    val listTitles = arrayOf("Shopping List", "Chores", "Android Tutorials")

    //@Overrides: RecyclerView.Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        /*Layout inflater is used to instantiate a layout XML file into View Objects*/
        val view = LayoutInflater.from(parent.context) /*create a layout programmatically*/
            .inflate(R.layout.list_selection_view_holder, parent, false)

        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int { /*Amount of items your RecyclerView will have*/
        return lists.size
    }

    /* Display the data in a specified position */
    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        /*if holder != nul complete this condition*/

        holder.listPosition.text = (position + 1).toString()
        holder.listTitle.text = lists.get(position).name
        holder.itemView.setOnClickListener{
            clickListener.listItemClicked(lists.get(position))
        }

    }

    //Helpers
    fun addList(list: TaskList) {
        lists.add(list)

        /*when a single item has its data updated but no positional changes have occurred*/
        notifyDataSetChanged()
    }

}