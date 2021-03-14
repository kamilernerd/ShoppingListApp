package com.kamil.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.kamil.shoppinglist.data.ListItem
import com.kamil.shoppinglist.databinding.FragmentListsLayoutBinding
import com.kamil.shoppinglist.databinding.FragmentListsListBinding

class ListsList : Fragment() {

    private var _binding: FragmentListsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListsListBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(listName: String, listItems: MutableList<ListItem>) =
            ListsList().apply {
                arguments = Bundle().apply {
                    _binding?.button1?.text = listName
                }
            }
    }
}