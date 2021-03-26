package com.kamil.shoppinglist.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.kamil.shoppinglist.ListCollectionAdapter
import com.kamil.shoppinglist.data.ListData
import java.util.*

class ListsCollectionViewModel(
    private val userId: String
) : ViewModel() {

    private val listsCollection: MutableList<ListData> = mutableListOf()

    private lateinit var reference: DatabaseReference
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
        listsCollection.removeAt(index)
    }

    fun getItems(): MutableList<ListData> {
        read()
        return listsCollection
    }

    fun read(): Task<DataSnapshot> {
        return database.child("lists").child(userId).get().addOnSuccessListener {
            if (it.exists()) {
                listsCollection.clear()
            }

            it.children.mapNotNullTo(listsCollection) {
                it.getValue<ListData>(ListData::class.java)
            }
        }
    }

    fun startReadingListener() {
        val listsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    listsCollection.clear()
                }

                dataSnapshot.children.mapNotNullTo(listsCollection) {
                    dataSnapshot.getValue<ListData>(ListData::class.java)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(listsListener)
    }

    companion object {
        private val DATABASE_PATH = "lists"
        private val TAG = "LIST_COLLECTION_VIEW_MODEL"
    }
}