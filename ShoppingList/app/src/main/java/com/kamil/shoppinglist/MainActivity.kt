package com.kamil.shoppinglist

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kamil.shoppinglist.data.ListData
import com.kamil.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var listsCollection: MutableList<ListData> = mutableListOf(
        ListData("0", "List 1"),
        ListData("1", "List 2"),
        ListData("2", "List 3"),
        ListData("3", "List 4"),
        ListData("4", "List 5"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listCollection.layoutManager = LinearLayoutManager(this)
        binding.listCollection.adapter = ListCollectionAdapter(listsCollection, this::onListTouch)

        binding.saveListButton.setOnClickListener {
            val title = binding.title.text.toString()
            binding.title.setText("")

            addNewList(title)
            val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun addNewList(title: String) {
        val list = ListData((listsCollection.size).toString(), title)
        listsCollection.add(list)
    }

    private fun onListTouch(position: Int): Unit {
        Log.println(Log.INFO, "Main activity", "HELLO LIST TOUCH!")
    }
}