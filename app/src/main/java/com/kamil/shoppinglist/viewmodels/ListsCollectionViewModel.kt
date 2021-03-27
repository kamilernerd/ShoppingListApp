package com.kamil.shoppinglist.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.kamil.shoppinglist.data.ListData

class ListsCollectionViewModel(
    private val userId: String
) : ViewModel() {

    private val listsCollection: MutableList<ListData> = mutableListOf()

    private var database = FirebaseDatabase.getInstance().reference

    init {
        database.keepSynced(true)
    }

    fun addList(title: String) {
        val listId = database.child(DATABASE_PATH).push().key
        listsCollection.add(ListData(listId.toString(), userId, title))

        database.child(DATABASE_PATH).child(userId).setValue(listsCollection).addOnSuccessListener {
            Log.println(Log.WARN, "ADD ITEM", "ok")
        }.addOnCanceledListener {
            Log.println(Log.WARN, "ADD ITEM", "not ok")
        }
    }

    fun deleteItem(index: Int) {
        if (listsCollection.isEmpty()) {
            return
        }

        listsCollection.removeAt(index)

        database.child(DATABASE_PATH).child(userId).setValue(listsCollection).addOnSuccessListener {
            Log.println(Log.WARN, "REMOVE", "REMOVED LIST")

            database.child(ListItemsViewModel.DATABASE_PATH).child(userId).child(index.toString()).setValue(null).addOnCompleteListener {
                Log.println(Log.WARN, "REMOVED", "REMOVED ALL CONNECTED ITEMS")
            }.addOnCanceledListener {
                Log.println(Log.WARN, "REMOVE", "COULD NOT REMOVE ALL CONNECTED ITEMS")
            }
        }.addOnCanceledListener {
            Log.println(Log.WARN, "REMOVE ITEM", "not ok")
        }
    }

    fun getItems(): MutableList<ListData> {
        return listsCollection
    }

    fun read(): Task<DataSnapshot> {
        return database.child(DATABASE_PATH).child(userId).get().addOnSuccessListener {
            if (it.exists()) {
                listsCollection.clear()
            }

            it.children.mapNotNullTo(listsCollection) {
                it.getValue<ListData>(ListData::class.java)
            }
        }
    }

    companion object {
        private val DATABASE_PATH = "lists"
        private val TAG = "LIST_COLLECTION_VIEW_MODEL"
    }
}