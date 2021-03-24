package com.kamil.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding
    private var listsCollectionViewModel: ListsCollectionViewModel = ListsCollectionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser == null) {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        } else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }



        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = ListCollectionAdapter(listsCollectionViewModel)

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