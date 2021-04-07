package com.kamil.shoppinglist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseUser
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
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

        binding.magicSpinner.visibility = View.VISIBLE

        listsCollectionViewModel.read().addOnCompleteListener {
            if (it.isComplete) {
                binding.magicSpinner.visibility = View.GONE
                listCollectionAdapter.notifyDataSetChanged()
            }
        }.addOnCanceledListener {
            // TODO
            // Add small text saying that list could not be fetched
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