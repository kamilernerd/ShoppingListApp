package com.kamil.shoppinglist.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.tasks.Tasks.await
import com.kamil.shoppinglist.R
import com.kamil.shoppinglist.databinding.AddNewItemDialogFragmentBinding
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel
import kotlinx.coroutines.CoroutineStart

class AddNewItemDialogFragment(
    private var listItemsViewModel: ListItemsViewModel,
    private val ListItemsAdapter: RecyclerView,
    private val progressBar: ProgressBar
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

        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.dialog_rounded_bg);

        binding.dialogCancelButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.dialogSaveButton.setOnClickListener {
            val itemName = binding.listNameInputField.text

            if (itemName.isEmpty() || itemName.length <= 0) {
                binding.listNameInputField.error = resources.getString(R.string.missingItemNameError);
                binding.listNameInputField.requestFocus();
            } else {
                listItemsViewModel.addItem(itemName.toString())
                listItemsAdapter.adapter?.notifyDataSetChanged()

                progressBar.max = progressBar.max + 1

                dialog?.hide()
                dialog?.cancel()
            }
        }
        return binding.root
    }

    companion object {
        const val TAG = "AddNewItemDialogFragment"
    }

}