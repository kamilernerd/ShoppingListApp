package com.kamil.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kamil.shoppinglist.databinding.ActivityMainBinding
import com.kamil.shoppinglist.dialogs.AddNewListDialogFragment
import com.kamil.shoppinglist.viewmodels.ListsCollectionViewModel

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding
    private var listsCollectionViewModel: ListsCollectionViewModel = ListsCollectionViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        auth = Firebase.auth
        val currentUser = auth.currentUser

        auth.signInWithEmailAndPassword("kamil.oracz98@gmail.com", "123456")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }

        if (currentUser == null) {
            setContentView(R.layout.fragment_auth)
        } else {
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