package com.kamil.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var listsCollectionViewModel: ListsCollectionViewModel = ListsCollectionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = ListCollectionAdapter(listsCollectionViewModel, this::onListTouch)

        binding.addNewButton.setOnClickListener {
            AddNewListDialogFragment(
                listsCollectionViewModel,
                binding.listCollection
            ).show(
                supportFragmentManager, AddNewListDialogFragment.TAG
            )
        }
    }

    private fun onListTouch(position: Int): Unit {
        Log.println(Log.INFO, "Main activity", "HELLO LIST TOUCH!")
    }
}