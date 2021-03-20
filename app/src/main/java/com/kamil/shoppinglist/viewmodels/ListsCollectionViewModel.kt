package com.kamil.shoppinglist.viewmodels

import androidx.lifecycle.ViewModel
import com.kamil.shoppinglist.ListCollectionAdapter
import com.kamil.shoppinglist.data.ListData

class ListsCollectionViewModel : ViewModel() {

    private var listsCollection: MutableList<ListData> = mutableListOf(
        ListData("0", "List 1"),
        ListData("1", "List 2"),
        ListData("2", "List 3"),
        ListData("3", "List 4"),
        ListData("4", "List 5"),
    )

    /**
     * Add new list
     * Automatically creates ID
     */
    fun addItem(title: String) {
        val list = ListData((listsCollection.size).toString(), title)
        listsCollection.add(list)
    }

    /**
     * Add new list
     */
    fun addItem(title: String, id: Int) {
        val list = ListData(id.toString(), title)
        listsCollection.add(list)
    }

    /**
     * Delete directly at index
     * and bind ViewHolder
     */
    fun deleteItem(index: Int, holder: ListCollectionAdapter.ViewHolder) {
        val newList = listsCollection.removeAt(index)
        holder.bind(newList)
    }

    /**
     * Delete directly at index
     * */
    fun deleteItem(index: Int) {
        listsCollection.removeAt(index)
    }

    /**
     * Get list collection reference
     */
    fun getItems(): MutableList<ListData> {
        return this.listsCollection
    }

}