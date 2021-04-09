package com.kamil.shoppinglist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kamil.shoppinglist.R
import com.kamil.shoppinglist.databinding.EditListItemDialogFragmentBinding
import com.kamil.shoppinglist.lists.ListItemsAdapter
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel

class EditListItemDialog(
    private val itemId: String,
    private var listItemsViewModel: ListItemsViewModel,
    private var listId: String,
    private val listItemsAdapter: ListItemsAdapter,
) : DialogFragment() {

    private var _binding: EditListItemDialogFragmentBinding? = null
    private val binding get() = _binding!!

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
        _binding = EditListItemDialogFragmentBinding.inflate(layoutInflater)

        binding.dialogCancelButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.dialogSaveButton.setOnClickListener {
            val itemName = binding.itemNameInputField.text

            if (itemName.isEmpty() || itemName.length <= 0) {
                binding.itemNameInputField.error = resources.getString(R.string.missingItemNameError);
                binding.itemNameInputField.requestFocus();
            } else {
                listItemsViewModel.updateItem(itemId, itemName.toString(), listId)
                listItemsAdapter.notifyDataSetChanged()

                dialog?.hide()
                dialog?.cancel()
            }
        }
        return binding.root
    }

    companion object {
        const val TAG = "EditListItemDialog"
    }

}