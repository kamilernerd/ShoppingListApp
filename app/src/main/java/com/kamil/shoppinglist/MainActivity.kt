package com.kamil.shoppinglist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private var listsCollectionViewModel: ListsCollectionViewModel = ListsCollectionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = ListCollectionAdapter(listsCollectionViewModel)

        val user = intent.getParcelableExtra<FirebaseUser>("USER")
        Log.println(Log.INFO, "HEI", "${user?.email}")

        binding.addNewButton.setOnClickListener {
            AddNewListDialogFragment(
                listsCollectionViewModel,
                binding.listCollection
            ).show(
                supportFragmentManager, AddNewListDialogFragment.TAG
            )
        }
    }
}