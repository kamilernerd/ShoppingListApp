package com.kamil.shoppinglist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.lists.ListCollectionAdapter
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var listsCollectionViewModel: ListsCollectionViewModel
    private lateinit var listCollectionAdapter: ListCollectionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<FirebaseUser>("USER")

        listsCollectionViewModel = ListsCollectionViewModel(user?.uid.toString())
        listCollectionAdapter = ListCollectionAdapter(listsCollectionViewModel)

        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = listCollectionAdapter
        binding.missingLists.visibility = View.GONE
        binding.magicSpinner.visibility = View.VISIBLE

        listsCollectionViewModel.read().addOnCompleteListener {
            if (it.isComplete) {
                if (it.result?.children?.count()!! <= 0) {
                    binding.missingLists.visibility = View.VISIBLE
                }
                binding.magicSpinner.visibility = View.GONE
                listCollectionAdapter.notifyDataSetChanged()
            }
        }.addOnCanceledListener {
            binding.missingLists.text = resources.getString(R.string.list_loading_failed)
            binding.missingLists.visibility = View.VISIBLE
        }

        binding.addNewButton.setOnClickListener {
            AddNewListDialogFragment(
                listsCollectionViewModel,
                binding.listCollection
            ).show(
                supportFragmentManager, AddNewListDialogFragment.TAG
            )
            listCollectionAdapter.notifyDataSetChanged()
        }
    }
}