package com.kamil.shoppinglist

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.databinding.ListContentViewFragmentBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class ListContentViewFragment(
    ListsCollectionViewModel: ListsCollectionViewModel,
    Position: String
) : Fragment() {

    private lateinit var _binding: ListContentViewFragmentBinding
    private val binding get() = _binding!!

    private val listCollectionViewModel = ListsCollectionViewModel
    private val position = Position

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = ListContentViewFragmentBinding.inflate(layoutInflater)

        Log.println(Log.WARN, "HEI:", "OPENED LIST AT POSITION ${this.position}")

        binding.listItems.layoutManager = LinearLayoutManager(context)
        binding.listItems.adapter = ListItemsAdapter(position)

        return binding.root
    }

    companion object {
        const val TAG = "ListContentViewFragment"
    }

}