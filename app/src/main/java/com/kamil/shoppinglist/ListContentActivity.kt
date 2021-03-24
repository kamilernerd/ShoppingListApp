package com.kamil.shoppinglist

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamil.shoppinglist.databinding.ListContentActivityBinding

class ListContentActivity : Activity() {

    private lateinit var binding: ListContentActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val position = intent.getStringExtra(LIST_ID).toString()
        val listName = intent.getStringExtra(LIST_NAME).toString()
        Log.println(Log.WARN, "HEI:", "OPENED LIST AT POSITION ${position}")

        binding.listNameTextView.text = listName
        binding.listItems.layoutManager = LinearLayoutManager(this)
        binding.listItems.adapter = ListItemsAdapter(position)
    }

    companion object {
        const val LIST_ID = "id"
        const val LIST_NAME = "name"
    }

}