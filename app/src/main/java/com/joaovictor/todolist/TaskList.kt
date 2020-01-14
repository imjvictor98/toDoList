package com.joaovictor.todolist

import android.os.Parcel
import android.os.Parcelable

//Store and retrieve data through Parcelable

class TaskList(val name: String?, val tasks: ArrayList<String> = ArrayList()) : Parcelable {

    //Constructor for Parcel
    constructor(source: Parcel) : this(source.readString(),
        source.createStringArrayList() as ArrayList<String>)
    /*Can be created from a passed-in Parcel*/

    //Variables


    //@Override: Parcelable
    override fun describeContents() = 0

    /*Put the object you might want store in a Parcel(packing)*/
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(tasks)
        //Packing the object
    }

    //Companion
    companion object CREATOR: Parcelable.Creator<TaskList> {
        //@Override: Parcelable.Creator
        override fun createFromParcel(source: Parcel): TaskList = TaskList(source)
        override fun newArray(size: Int): Array<TaskList?> = arrayOfNulls(size)
    }

    //Helpers


}
