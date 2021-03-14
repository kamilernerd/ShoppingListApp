package com.kamil.shoppinglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ListLayoutBinding

class ListCollectionAdapter(private val lists: MutableList<ListData>,
                            private val onBookClicked:(ListData) -> Unit
): RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ListData) {
            binding.listId.text = list.id
            binding.listName.text = list.listName
        }
    }

    override fun getItemCount(): Int = lists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = lists[position]
        holder.bind(book)
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
        lists.clear()
        lists.addAll(newLists)
        notifyDataSetChanged()
    }
}