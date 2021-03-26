package com.kamil.shoppinglist

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel
import kotlinx.android.synthetic.main.activity_main.*

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
        Log.println(Log.INFO, "HEI", "${user?.email}")

        listsCollectionViewModel = ListsCollectionViewModel(user?.uid.toString())
        listCollectionAdapter = ListCollectionAdapter(listsCollectionViewModel)

        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = listCollectionAdapter

        // Show spinner and hide list
        binding.magicSpinner.visibility = View.VISIBLE
        binding.listCollectionParentContainer.visibility = View.GONE

        listsCollectionViewModel.read().addOnCompleteListener {
            if (it.isComplete) {
                listCollectionAdapter.notifyDataSetChanged()
                binding.magicSpinner.visibility = View.GONE
                binding.listCollectionParentContainer.visibility = View.VISIBLE
            }
        }

        binding.addNewButton.setOnClickListener {
            AddNewListDialogFragment(
                listsCollectionViewModel,
                binding.listCollection
            ).show(
                supportFragmentManager, AddNewListDialogFragment.TAG
            )
//            listCollectionAdapter.notifyDataSetChanged()
        }
    }
}