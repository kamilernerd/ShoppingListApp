package com.kamil.shoppinglist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kamil.shoppinglist.R
import com.kamil.shoppinglist.databinding.EditListDialogFragmentBinding
import com.kamil.shoppinglist.lists.ListCollectionAdapter
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class EditListDialog(
    private var listId: String,
    private var listsCollectionViewModel: ListsCollectionViewModel,
    private val listCollectionAdapter: ListCollectionAdapter
) : DialogFragment() {

    private var _binding: EditListDialogFragmentBinding? = null
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
        _binding = EditListDialogFragmentBinding.inflate(layoutInflater)

        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        binding.dialogCancelButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.dialogSaveButton.setOnClickListener {
            val itemName = binding.listNameInputField.text

            if (itemName.isEmpty() || itemName.length <= 0) {
                binding.listNameInputField.error = resources.getString(R.string.missingFieldNameError);
                binding.listNameInputField.requestFocus();
            } else {
                listsCollectionViewModel.updateList(itemName.toString(), listId)
                listCollectionAdapter.notifyDataSetChanged()

                dialog?.hide()
                dialog?.cancel()
            }
        }
        return binding.root
    }

    companion object {
        const val TAG = "EditListDialog"
    }

}