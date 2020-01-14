package com.joaovictor.todolist

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(val context: Context) {


    /*Save the data(list) in internal storage*/
    fun saveList(list: TaskList){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()

        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()

    }

    /*Read the data(list) from internal storage*/
    fun readLists(): ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferencesContents = sharedPreferences.all

        val taskLists = ArrayList<TaskList>()

        for(taskList in sharedPreferencesContents) {
            val itemHashSet = taskList.value as HashSet<String>
            val list = TaskList(taskList.key, ArrayList(itemHashSet))
            taskLists.add(list)
        }

        return taskLists
    }
}