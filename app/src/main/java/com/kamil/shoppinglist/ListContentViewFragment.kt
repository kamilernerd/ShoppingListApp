package com.kamil.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.databinding.ListContentViewFragmentBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class ListContentViewFragment(
    private val listsCollectionViewModel: ListsCollectionViewModel,
    private val ListCollectionAdapter: RecyclerView
) : Fragment() {

    private var _binding: ListContentViewFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        _binding = ListContentViewFragmentBinding.inflate(layoutInflater)

        return binding.root
    }

}