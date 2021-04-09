package com.kamil.shoppinglist.lists

import android.R
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.data.ListItem
import com.kamil.shoppinglist.databinding.ListItemLayoutBinding
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel
import kotlinx.android.synthetic.main.list_item_layout.view.*


class ListItemsAdapter(
    private val listItemsViewModel: ListItemsViewModel,
    private val ListId: String,
    private val progressBar: ProgressBar,
    private val openEditItemDialog: (id: String, listId: String) -> Unit
): RecyclerView.Adapter<ListItemsAdapter.ViewHolder>() {

    private val listId = ListId
    private lateinit var mDetector: GestureDetectorCompat

    private class ItemGestureListener(
        private val holder: ViewHolder,
        private val openEditItemDialog: (id: String, listId: String) -> Unit,
        private val listId: String,
    ) : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
            super.onSingleTapConfirmed(e)
            Toast.makeText(holder.itemView.context, "Double tap to edit", Toast.LENGTH_SHORT).show()
            return true
        }

        override fun onDoubleTap(e: MotionEvent?): Boolean {
            super.onDoubleTap(e)

            openEditItemDialog(holder.adapterPosition.toString(), listId)
            return true
        }
    }


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

        mDetector = GestureDetectorCompat(
            holder.itemView.context,
            ItemGestureListener(
                holder,
                openEditItemDialog,
                listId
        ))

        holder.binding.listItemCard.setOnTouchListener(OnTouchListener { v, event ->
            mDetector.onTouchEvent(event)
        })

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