package com.joaovictor.todolist

/*  Fragment is a part of an activity'user interface
* */
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joaovictor.todolist.ViewAdapter.ListSelectionRecyclerViewAdapter

class ListSelectionFragment : Fragment(),
    ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView

    private var listener: OnListItemFragmentInteractionListener? = null /*Holds a reference to an object that implements the Fragment Interface*/

    interface OnListItemFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }


    /*Used by any object that wants to create a new instance of this fragment*/
    companion object {
       fun newInstance(): ListSelectionFragment {
           val fragment = ListSelectionFragment()
           return fragment
       }
    }


    /*This method runs when the Fragment is associated with an activity*/
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnListItemFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
        } else {
            throw RuntimeException(context.toString() + " must implement OnListItemFragmentInteractionListener")
        }
    }


    /*In process of being created*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    /*Fragment acquires a layout to be present within activity*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*When this screen shows up it reads every data on internal storage*/
        val lists = listDataManager.readLists()

        view?.let {
            listsRecyclerView = it.findViewById(R.id.lists_recyclerview)
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            /* Try to pass data to our adapter */
            listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        }


    }

    /*Is called when a fragment is no longer attached to an Activity*/
    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    override fun listItemClicked(list: TaskList) {
        listener?.onListItemClicked(list)
    }


    fun addList(list: TaskList) {
        listDataManager.saveList(list)

        val recycleAdapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        recycleAdapter.addList(list)
    }


    fun saveList(list: TaskList) {
        listDataManager.saveList(list)

        updateLists()
    }

    fun updateLists() {
        val lists = listDataManager.readLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

}
