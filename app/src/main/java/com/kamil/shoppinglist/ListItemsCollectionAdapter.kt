package com.kamil.shoppinglist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ListLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class ListItemsCollectionAdapter(
    private val listsCollectionViewModel: ListsCollectionViewModel,
    private val onListTouch: (position: Int) -> Unit
): RecyclerView.Adapter<ListItemsCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        // Assign values to card view
        fun bind(list: ListData) {
            binding.listId.text = list.id
            binding.listName.text = list.listName
        }
    }

    override fun getItemCount(): Int = listsCollectionViewModel.getItems().size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listsCollectionViewModel.getItems()[position]
        holder.bind(list)

        holder.itemView.setOnClickListener {
            this.onListTouch(position)
        }

        // Delete list from recycler view
        holder.binding.deleteListButton.setOnClickListener {
            Log.println(Log.INFO, "REMOVET AT INDEX ", position.toString())
            Log.println(Log.INFO, "REMOVET LIST AT ", listsCollectionViewModel.getItems()[position].listName.toString())

            listsCollectionViewModel.deleteItem(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    public fun updateCollection(newLists: List<ListData>) {
        listsCollectionViewModel.getItems().clear()
        listsCollectionViewModel.getItems().addAll(newLists)
        notifyDataSetChanged()
    }
}