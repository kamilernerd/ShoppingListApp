package com.kamil.shoppinglist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ListLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class ListCollectionAdapter(
    private val listsCollectionViewModel: ListsCollectionViewModel
): RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>() {

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

        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val view = v?.context as AppCompatActivity
                view.supportFragmentManager.beginTransaction().replace(
                    R.id.listCollectionParentContainer,
                    ListContentViewFragment(
                        listsCollectionViewModel,
                        position.toString()
                    )
                ).addToBackStack(ListContentViewFragment.TAG).commit()
            }
        })

        // Delete list from recycler view
        holder.binding.deleteListButton.setOnClickListener {
            Log.println(Log.INFO, "REMOVET AT INDEX ", position.toString())
            Log.println(Log.INFO, "REMOVET LIST AT ", listsCollectionViewModel.getItems()[position].listName.toString())

            listsCollectionViewModel.deleteItem(position, holder)
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