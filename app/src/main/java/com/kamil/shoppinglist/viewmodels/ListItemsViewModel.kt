package com.kamil.shoppinglist.viewmodels

import androidx.lifecycle.ViewModel
import com.kamil.shoppinglist.ListCollectionAdapter
import com.kamil.shoppinglist.ListItemsAdapter
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.data.ListItem

class ListItemsViewModel : ViewModel() {

    private var listItems: MutableList<ListItem> = mutableListOf(
        ListItem("0", "List 1", "1"),
        ListItem("1", "List 2", "1"),
        ListItem("2", "List 3", "1"),
        ListItem("3", "List 4", "2"),
        ListItem("4", "List 5", "2"),
    )

    /**
     * Add new list
     * Automatically creates ID
     */
    fun addItem(title: String) {
        val listItem = ListItem((listItems.size).toString(), title)
        listItems.add(listItem)
    }

    /**
     * Add new list
     */
    fun addItem(title: String, id: Int, listId: String) {
        val listItem = ListItem(id.toString(), title, listId)
        listItems.add(listItem)
    }

    /**
     * Delete directly at index
     * and bind ViewHolder
     */
    fun deleteItem(index: Int, holder: ListItemsAdapter.ViewHolder) {
        val newList = listItems.removeAt(index)
        holder.bind(newList)
    }

    /**
     * Delete directly at index
     * */
    fun deleteItem(index: Int) {
        listItems.removeAt(index)
    }

    /**
     * Get list collection reference
     */
    fun getItems(): MutableList<ListItem> {
        return this.listItems
    }

}