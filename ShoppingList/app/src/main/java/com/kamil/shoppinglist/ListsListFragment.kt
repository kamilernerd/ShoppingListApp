package com.kamil.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.kamil.shoppinglist.data.ListItem
import com.kamil.shoppinglist.databinding.FragmentListsListBinding

class ListsListFragment : Fragment() {

    private var _binding: FragmentListsListBinding? = null
    private val binding get() = _binding!!

    private var listName: String? = null

    var onKeyPressed: ((listName: String) -> Unit?)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listName = it.getString("listName") ?: "no note..."
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListsListBinding.inflate(layoutInflater)

        binding.listItem.text = listName

        binding.listItem.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                listName?.let { this@ListsListFragment.onKeyPressed?.invoke(it) }
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(listName: String, listItems: MutableList<ListItem>) =
            ListsListFragment().apply {
                arguments = Bundle().apply {
                    putString("listName", listName)
                }
            }
    }
}