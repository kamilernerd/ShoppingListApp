package com.kamil.shoppinglist

import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.ListContentActivity.Companion.LIST_ID
import com.kamil.shoppinglist.ListContentActivity.Companion.LIST_NAME
import com.kamil.shoppinglist.ListContentActivity.Companion.USER_ID
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ListLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class ListCollectionAdapter(
    private val listsCollectionViewModel: ListsCollectionViewModel
): RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>() {

    init {
        notifyDataSetChanged()
    }

    // Map list name to list item in the view
    class ViewHolder(val binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ListData) {
            binding.listId.text = list.id
            binding.listName.text = list.listName
        }
    }

    override fun getItemCount(): Int = listsCollectionViewModel.getItems().count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listsCollectionViewModel.getItems()[position]
        holder.bind(list)

        // Open new activity when clicking on a list
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val view = v?.context as AppCompatActivity

                val intent = Intent(view, ListContentActivity::class.java)
                    .putExtra(LIST_ID, position.toString())
                    .putExtra(LIST_NAME, list.listName)
                    .putExtra(USER_ID, list.userId)
                view.startActivity(intent)
            }
        })

        // Delete list from recycler view
        holder.binding.deleteListButton.setOnClickListener {
            listsCollectionViewModel.deleteItem(position)
            notifyItemRemoved(position)
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
}