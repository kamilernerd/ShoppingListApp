package com.kamil.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kamil.shoppinglist.databinding.FragmentAuthBinding

class AuthFragment : AppCompatActivity() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = FragmentAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        var email = binding.editTextTextEmailAddress.text.toString()
        var password = binding.editTextTextPassword.text.toString()

        email = "kamil.oracz98@gmail.com"
        password = "123456"


        binding.signInButton.setOnClickListener {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(AuthFragment.TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        finish() // Make sure to kill current activity
                        startActivity(Intent(this, MainActivity::class.java).putExtra("USER", user).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(AuthFragment.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    companion object {
        const val TAG = "AuthFragment"
    }
}