package com.joaovictor.todolist.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.joaovictor.todolist.ViewAdapter.ListItemRecyclerViewAdapter
import com.joaovictor.todolist.R
import com.joaovictor.todolist.TaskList

class ListDetailActivity : AppCompatActivity() {

    //Variables
    lateinit var list: TaskList
    lateinit var listItemsRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton

    //@Override: AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        /*When this screen shows up try to get the data passed through intent*/
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)

        /*Put the title of your toolbar screen equals to name of list*/
        title = list.name

        /* Finds recycler view in Activity Layout*/
        listItemsRecyclerView = findViewById(R.id.list_items_recyclerview)

        /*Assign an adapter to this Recycler View*/
        listItemsRecyclerView.adapter =
            ListItemRecyclerViewAdapter(list)

        listItemsRecyclerView.layoutManager = LinearLayoutManager(this)

        addTaskButton.findViewById<FloatingActionButton>(R.id.add_task_button)
        addTaskButton.setOnClickListener{
            showCreateTaskDialog()
        }
    }

    override fun onBackPressed() {
        /*Whenever a back button is tapped you package up the list in its current state and let
        * MainActivity knows that everything is OK */

        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()

    }

    //Helpers
    private fun showCreateTaskDialog() {
        /*It will receive text from user*/
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT


        /*Creates a Dialog for user to insert a specified task*/
        AlertDialog.Builder(this)
            .setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.add_task) { dialog, _ ->
                /*Grab input and create a task*/
                val task = taskEditText.text.toString()
                list.tasks.add(task)

                /*Notify the adapter that a new item has been added*/
                val recyclerAdapter = listItemsRecyclerView.adapter as ListItemRecyclerViewAdapter
                recyclerAdapter.notifyItemInserted(list.tasks.size)

                dialog.dismiss()
            }
            .create()
            .show()

    }
}
