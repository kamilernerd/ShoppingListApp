package com.kamil.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamil.shoppinglist.data.ListItem
import com.kamil.shoppinglist.data.ShoppingList
import com.kamil.shoppinglist.databinding.FragmentListsLayoutBinding

class ListsLayoutFragment : Fragment() {

    private var _binding: FragmentListsLayoutBinding? = null
    private val binding get() = _binding!!

    private var itemsListSet: MutableList<ListItem> = mutableListOf(
        ListItem("Item1"),
        ListItem("Item2"),
        ListItem("Item3"),
        ListItem("Item4")
    )

    private var shoppingListSet: MutableList<ShoppingList> = mutableListOf(
        ShoppingList("List1", listItems = itemsListSet),
        ShoppingList("List2", listItems = itemsListSet),
        ShoppingList("List3", listItems = itemsListSet),
        ShoppingList("List4", listItems = itemsListSet),
        ShoppingList("List5", listItems = itemsListSet),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListsLayoutBinding.inflate(layoutInflater)

        var fragmentManager = childFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()

        shoppingListSet.forEach {
            val listsList = ListsListFragment.newInstance(it.listName, it.listItems)

            listsList.onKeyPressed = {
                print("HEI")
            }

            fragmentTransaction.add(binding.listsList.id, listsList, it.listName)
        }

        fragmentTransaction.commit()

        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            ListsLayoutFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}