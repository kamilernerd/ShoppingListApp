package com.kamil.shoppinglist.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.data.ListItem
import com.kamil.shoppinglist.databinding.ListItemLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel
import kotlinx.android.synthetic.main.list_item_layout.view.*

class ListItemsAdapter(
    private val listItemsViewModel: ListItemsViewModel,
    private val ListId: String,
    private val progressBar: ProgressBar
): RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    private val listId = ListId

    class ViewHolder(val binding: ListItemLayoutBinding, val listId: String) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem) {
            if (!listId.equals(item.listId)) {
                return
            }

            binding.itemName.text = item.itemName
            binding.checkBox.isChecked = item.checked
        }
    }

    override fun getItemCount(): Int = listItemsViewModel.getItems().count()

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        val itemsList = listItemsViewModel.getItems()[holder.adapterPosition]
        holder.bind(itemsList)

        holder.binding.checkBox.setOnClickListener {
            listItemsViewModel.checkUncheckItem(holder.adapterPosition)

            progressBar.max = listItemsViewModel.getItems().count()

            if (it?.checkBox?.isChecked == true) {
                progressBar.progress = progressBar.progress + 1
            } else {
                progressBar.progress = progressBar.progress - 1
            }
        }

        holder.binding.deleteListButton.setOnClickListener {
            if (holder.adapterPosition <= RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }

            listItemsViewModel.deleteItem(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
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
}