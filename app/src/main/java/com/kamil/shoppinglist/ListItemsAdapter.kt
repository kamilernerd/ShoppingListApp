package com.kamil.shoppinglist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.data.ListItem
import com.kamil.shoppinglist.databinding.ListItemLayoutBinding
import com.kamil.shoppinglist.databinding.ListLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel

class ListItemsAdapter(
    private val listItemsViewModel: ListItemsViewModel,
    private val ListId: String
): RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    private val listId = ListId

    class ViewHolder(val binding: ListItemLayoutBinding, val listId: String) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem) {
            if (!listId.equals(item.listId)) {
                return
            }

            binding.itemId.text = item.id
            binding.itemName.text = item.itemName
            binding.listId.text = item.listId
        }
    }

    override fun getItemCount(): Int = listItemsViewModel.getItems().size

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        val itemsList = listItemsViewModel.getItems()[itemPosition]
        holder.bind(itemsList)

        holder.binding.deleteListButton.setOnClickListener {
            listItemsViewModel.deleteItem(itemPosition)
            notifyItemRemoved(itemPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listId,
        )
    }

    public fun updateCollection(newLists: List<ListItem>) {
        //listItemsViewModel.getItems().clear()
        //listItemsViewModel.getItems().addAll(newLists)
        //listItemsViewModel.getItems().
        //notifyDataSetChanged()
    }
}