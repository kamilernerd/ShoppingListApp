package com.kamil.shoppinglist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamil.shoppinglist.databinding.ListContentActivityBinding
import com.kamil.shoppinglist.dialogs.AddNewItemDialogFragment
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
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
        Log.println(Log.WARN, "HEI:", "OPENED LIST AT POSITION ${position}")

        listsItemsViewModel = ListItemsViewModel(position, userId)
        listItemsAdapter = ListItemsAdapter(listsItemsViewModel, position)

        binding.listNameTextView.text = listName
        binding.listItems.layoutManager = LinearLayoutManager(this)
        binding.listItems.adapter = listItemsAdapter

        // Show spinner and hide list
        binding.magicSpinner.visibility = View.VISIBLE
        binding.listItemsParentContainer.visibility = View.GONE
        binding.emptyListLayout.visibility = View.GONE

        listsItemsViewModel.read().addOnCompleteListener {
            if (it.isComplete) {
                listItemsAdapter.notifyDataSetChanged()
                binding.magicSpinner.visibility = View.GONE
                binding.listItemsParentContainer.visibility = View.VISIBLE

                if (it.result?.childrenCount!! > 0) {
                    binding.emptyListLayout.visibility = View.GONE
                    binding.listItemsParentContainer.visibility = View.VISIBLE
                } else {
                    binding.emptyListLayout.visibility = View.VISIBLE
                    binding.listItemsParentContainer.visibility = View.GONE
                }
            }

        }.addOnCanceledListener {
            // TODO
            // Add small text saying that list could not be fetched
        }

        binding.addNewItem.setOnClickListener {
            AddNewItemDialogFragment(
                listsItemsViewModel,
                binding.listItems
            ).show(
                supportFragmentManager, AddNewListDialogFragment.TAG
            )
        }
    }

    companion object {
        const val LIST_ID = "id"
        const val LIST_NAME = "name"
        const val USER_ID = "user_id"
    }

}