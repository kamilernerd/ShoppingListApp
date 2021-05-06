package com.kamil.shoppinglist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamil.shoppinglist.databinding.ListContentActivityBinding
import com.kamil.shoppinglist.dialogs.AddNewItemDialogFragment
import com.kamil.shoppinglist.dialogs.EditListItemDialog
import com.kamil.shoppinglist.lists.ListItemsAdapter
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel

class ListContentActivity : AppCompatActivity() {

    private lateinit var binding: ListContentActivityBinding
    private lateinit var listsItemsViewModel: ListItemsViewModel
    private lateinit var listItemsAdapter: ListItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val position = intent.getStringExtra(LIST_ID).toString()
        val listName = intent.getStringExtra(LIST_NAME).toString()
        val userId = intent.getStringExtra(USER_ID).toString()

        listsItemsViewModel = ListItemsViewModel(position, userId)

        listItemsAdapter = ListItemsAdapter(listsItemsViewModel, position, binding.progressBar, this::openEditItemFragment)


        listsItemsViewModel.read().addOnCompleteListener {
            if (it.isComplete) {
                binding.magicSpinner.visibility = View.GONE
                binding.progressBar.max = listsItemsViewModel.getItems().count()
                binding.progressBar.progress = listsItemsViewModel.getAllCheckedItems().count()
                listItemsAdapter.notifyDataSetChanged()
            }
        }

        binding.listNameTextView.text = listName
        binding.listItems.layoutManager = LinearLayoutManager(this)
        binding.listItems.adapter = listItemsAdapter

        binding.magicSpinner.visibility = View.VISIBLE

        binding.addNewItem.setOnClickListener {
            AddNewItemDialogFragment(
                listsItemsViewModel,
                binding.listItems,
                binding.progressBar
            ).show(
                supportFragmentManager, AddNewItemDialogFragment.TAG
            )
            listItemsAdapter.notifyDataSetChanged()
            binding.progressBar.max = listsItemsViewModel.getItems().count()
            binding.progressBar.progress = listsItemsViewModel.getAllCheckedItems().count()
        }
    }

    fun openEditItemFragment(listItemId: String, listId: String) {
        EditListItemDialog(listItemId, listsItemsViewModel, listId, listItemsAdapter).show(
            supportFragmentManager, EditListItemDialog.TAG
        )
    }

    companion object {
        const val LIST_ID = "id"
        const val LIST_NAME = "name"
        const val USER_ID = "user_id"
    }

}
