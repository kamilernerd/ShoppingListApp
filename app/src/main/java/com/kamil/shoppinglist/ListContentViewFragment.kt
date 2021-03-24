package com.kamil.shoppinglist

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamil.shoppinglist.databinding.ListContentViewFragmentBinding

class ListContentViewFragment : Activity() {

    private lateinit var binding: ListContentViewFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ListContentViewFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val position = intent.getStringExtra(LIST_ID).toString()
        Log.println(Log.WARN, "HEI:", "OPENED LIST AT POSITION ${position}")

        binding.listItems.layoutManager = LinearLayoutManager(this)
        binding.listItems.adapter = ListItemsAdapter(position)
    }

    companion object {
        const val LIST_ID = "id"
    }

}