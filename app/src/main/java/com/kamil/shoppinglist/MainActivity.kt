package com.kamil.shoppinglist

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.viewmodels.ListItemsViewModel
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

        listsCollectionViewModel = ListsCollectionViewModel(user?.uid.toString())
        listCollectionAdapter = ListCollectionAdapter(listsCollectionViewModel)

        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = listCollectionAdapter

//        val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                Log.println(Log.VERBOSE, "POSITION", target.layoutPosition.toString())
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                Log.println(Log.VERBOSE, "POSITION", direction.toString())
//
//            }
//        })
//
//        itemTouchHelper.attachToRecyclerView(binding.listCollection)

        // Show spinner and hide list
        binding.magicSpinner.visibility = View.VISIBLE
        binding.listCollectionParentContainer.visibility = View.GONE
        binding.emptyListLayout.visibility = View.GONE

        listsCollectionViewModel.read().addOnCompleteListener {
            listCollectionAdapter.notifyDataSetChanged()

            if (it.isComplete) {
                binding.magicSpinner.visibility = View.GONE

                if (it.result?.childrenCount!! > 0) {
                    binding.emptyListLayout.visibility = View.GONE
                    binding.listCollectionParentContainer.visibility = View.VISIBLE
                } else {
                    binding.emptyListLayout.visibility = View.VISIBLE
                    binding.listCollectionParentContainer.visibility = View.GONE
                }

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
        }
    }
}