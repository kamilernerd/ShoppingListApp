package com.kamil.shoppinglist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.kamil.shoppinglist.R
import com.kamil.shoppinglist.databinding.AddNewItemDialogFragmentBinding
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel

class AddNewItemDialogFragment(
    private var listItemsViewModel: ListItemsViewModel,
    private val ListItemsAdapter: RecyclerView
) : DialogFragment() {

    private var _binding: AddNewItemDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private var listItemsAdapter = ListItemsAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNewItemDialogFragmentBinding.inflate(layoutInflater)

        binding.dialogCancelButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.dialogSaveButton.setOnClickListener {
            val itemName = binding.itemNameInputField.text

            if (itemName.isEmpty() || itemName.length <= 0) {
                binding.itemNameInputField.error = resources.getString(R.string.missingItemNameError);
                binding.itemNameInputField.requestFocus();
            } else {
                listItemsViewModel.addItem(itemName.toString())
                listItemsAdapter.adapter?.notifyDataSetChanged()
                dialog?.hide()
                dialog?.cancel()
            }
        }

        return binding.root
    }

    companion object {
        const val TAG = "AddNewListDialogFragment"
    }

}