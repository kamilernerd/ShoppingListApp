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
import com.kamil.shoppinglist.databinding.AddNewListDialogFragmentBinding
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class AddNewListDialogFragment(
    private var listsCollectionViewModel: ListsCollectionViewModel,
    private val ListCollectionAdapter: RecyclerView
) : DialogFragment() {

    private var _binding: AddNewListDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private var listCollectionAdapter = ListCollectionAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

//        dialog?.window?.setLayout(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT / 2
//        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddNewListDialogFragmentBinding.inflate(layoutInflater)

        binding.dialogCancelButton.setOnClickListener {
            dialog?.dismiss()
        }

        binding.dialogSaveButton.setOnClickListener {

            val listName = binding.listNameInputField.text

            if (listName.isEmpty() || listName.length <= 0) {
                binding.listNameInputField.error = resources.getString(R.string.missingFieldNameError);
                binding.listNameInputField.requestFocus();
            } else {
                listsCollectionViewModel.addList(listName.toString())
                listCollectionAdapter.adapter?.notifyDataSetChanged()
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