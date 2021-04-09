package com.kamil.shoppinglist.lists

import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.ListContentActivity
import com.kamil.shoppinglist.ListContentActivity.Companion.LIST_ID
import com.kamil.shoppinglist.ListContentActivity.Companion.LIST_NAME
import com.kamil.shoppinglist.ListContentActivity.Companion.USER_ID
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ListLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class ListCollectionAdapter(
    private val listsCollectionViewModel: ListsCollectionViewModel,
    private val openEditListFragment: (listId: String) -> Unit
): RecyclerView.Adapter<ListCollectionAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ListData) {
            binding.listName.text = list.listName
        }
    }

    override fun getItemCount(): Int = listsCollectionViewModel.getItems().count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = listsCollectionViewModel.getItems()[holder.adapterPosition]
        holder.bind(list)

        holder.itemView.setOnLongClickListener {
            val vibrator =
                ContextCompat.getSystemService(holder.itemView.context, Vibrator::class.java)
            vibrator?.let {
                if (Build.VERSION.SDK_INT >= 26) {
                    it.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    it.vibrate(100)
                }
            }
            openEditListFragment(holder.adapterPosition.toString())
            return@setOnLongClickListener true
        }

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
            if (holder.adapterPosition <= RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }

            listsCollectionViewModel.deleteItem(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
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