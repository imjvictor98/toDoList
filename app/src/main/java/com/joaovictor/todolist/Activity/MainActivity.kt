package com.joaovictor.todolist.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.ListDataManager
import com.joaovictor.todolist.ListSelectionFragment
import com.joaovictor.todolist.ViewAdapter.ListSelectionRecyclerViewAdapter
import com.joaovictor.todolist.R
import com.joaovictor.todolist.TaskList

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ListSelectionFragment.OnListItemFragmentInteractionListener {

    //region Static
    companion object {
        /*Used to refer to a list whenever it needs to pass one to a new Activity*/
        val INTENT_LIST_KEY = "list"
        val LIST_DETAIL_REQUEST_CODE = 123
    }
    //endregion

    //region Variables
    private var listSelectionFragment: ListSelectionFragment = ListSelectionFragment.newInstance()
    private var fragmentContainer: FrameLayout? = null
    //endregion

    //region Override: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            showCreateListDialog()
        }

        fragmentContainer = findViewById(R.id.fragment_container)


        supportFragmentManager/*Lets you add dynamically fragments in runtime*/
            .beginTransaction()
            .add(R.id.fragment_container, listSelectionFragment)
            .commit()
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

    //Interface Implementation
    /*When an item of list is clicked*/
    override fun onListItemClicked(list: TaskList) {
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
                listSelectionFragment.saveList(data.getParcelableExtra(INTENT_LIST_KEY)!!)
            }
        }
    }
    //endregion

    //region Helpers
    //Helpers
    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, i ->
            val list =
                TaskList(listTitleEditText.text.toString())
            listSelectionFragment.addList(list) //We save data(list) in internal storage

            dialog.dismiss()
            showListDetail(list) /*When we finish to input the name it launches a new Activity*/
        }

        builder.create().show()

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
    //endregion
}
