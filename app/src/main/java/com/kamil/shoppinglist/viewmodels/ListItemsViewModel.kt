package com.kamil.shoppinglist.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.kamil.shoppinglist.data.ListItem

class ListItemsViewModel(
    private val listId: String,
    private val userId: String
) : ViewModel() {

    private var listItems: MutableList<ListItem> = mutableListOf()

    private var database = FirebaseDatabase.getInstance().reference

    init {
        database.keepSynced(true)
    }

    fun addItem(title: String) {
        val itemId = database.child(DATABASE_PATH).push().key
        listItems.add(ListItem(itemId.toString(), title, listId, false))

        database.child(DATABASE_PATH).child(userId).child(listId).setValue(listItems).addOnSuccessListener {
            Log.println(Log.WARN, "ADD ITEM", "ok")
        }.addOnCanceledListener {
            Log.println(Log.WARN, "ADD ITEM", "not ok")
        }
    }

    fun deleteItem(index: Int) {
        if (listItems.isEmpty()) {
            return
        }

        listItems.removeAt(index)
        database.child(DATABASE_PATH).child(userId).child(listId).setValue(listItems).addOnSuccessListener {
            Log.println(Log.WARN, "REMOVE ITEM", "ok")
        }.addOnCanceledListener {
            Log.println(Log.WARN, "REMOVE ITEM", "not ok")
        }
    }

    fun getAllCheckedItems(): List<ListItem> {
        return listItems.filter {
            it.checked
        }
    }

    fun checkUncheckItem(index: Int) {
        listItems[index].checked = !listItems[index].checked
        database.child(DATABASE_PATH).child(userId).child(listId).setValue(listItems).addOnSuccessListener {
            Log.println(Log.WARN, "CHECKED ITEM", "ok checked ${listItems[index].checked}")
        }.addOnCanceledListener {
            Log.println(Log.WARN, "CHECKED ITEM", "not ok ${listItems[index].checked}")
        }
    }

    fun getItems(): MutableList<ListItem> {
        return listItems
    }

    fun read(): Task<DataSnapshot> {
        return database.child(DATABASE_PATH).child(userId).child(listId).get().addOnSuccessListener {
            if (it.exists()) {
                listItems.clear()
            }

            it.children.mapNotNullTo(listItems) {
                it.getValue<ListItem>(ListItem::class.java)
            }
        }
    }

    companion object {
        public val DATABASE_PATH = "listItems"
        private val TAG = "LIST_ITEMS_VIEW_MODEL"
    }

}