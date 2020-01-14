package com.joaovictor.todolist.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.ListDataManager
import com.joaovictor.todolist.ViewAdapter.ListSelectionRecyclerViewAdapter
import com.joaovictor.todolist.R
import com.joaovictor.todolist.TaskList

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    //Companion
    companion object {
        /*Used to refer to a list whenever it needs to pass one to a new Activity*/
        val INTENT_LIST_KEY = "list"
        val LIST_DETAIL_REQUEST_CODE = 123
    }

    //Variables
    lateinit var listsRecyclerView: RecyclerView

    val listDataManager: ListDataManager = ListDataManager(this)

    //@Override: AppCompatActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            showCreateListDialog()
        }

        /*When this screen shows up it reads every data on internal storage*/
        val lists = listDataManager.readLists()


        listsRecyclerView = findViewById(R.id.lists_recyclerview)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        /* Try to pass data to our adapter */
        listsRecyclerView.adapter =
            ListSelectionRecyclerViewAdapter(
                lists,
                this
            )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    //@Override: Interface Implementation
    /*When an item of list is clicked*/
    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }

    /* Allows MainActivity receive the result of any Activities it starts*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*You check if the code is the samecode you're expecting get back*/
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            /*Unwrapping data Intent passed-in*/
            data?.let {_ -> /*the let is a method when data is not null execute some block of code*/
                /*Save the list in internal storage and call updateLists()*/
                listDataManager.saveList(data.getParcelableExtra(INTENT_LIST_KEY))
                updateLists()
            }
        }
    }

    //Helpers
    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list =
                TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list) //We save data(list) in internal storage

            val recyclerAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)

            dialog.dismiss()
            showListDetail(list) /*When we finish to input the name it launches a new Activity*/
        }

        builder.create().show()

    }

    private fun updateLists() {

    }

    /* Intent Setup */
    private fun showListDetail(list: TaskList) {
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        /*Wait for response of ListDetailActivity once it has finished
        * Ask for someone do something to you and when it's over, it notifies me*/
        startActivityForResult(listDetailIntent,
            LIST_DETAIL_REQUEST_CODE
        )
    }
}
