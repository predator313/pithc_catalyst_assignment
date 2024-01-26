package com.aamirashraf.pitch_catalyst_assignment

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamirashraf.adapter.MyAdapter
import com.aamirashraf.model.Item
import com.aamirashraf.pitch_catalyst_assignment.databinding.ActivityMainBinding
import com.aamirashraf.pitch_catalyst_assignment.viewmodel.ItemViewModel
import com.aamirashraf.pitch_catalyst_assignment.viewmodel.ItemViewModelFactory
import com.aamirashraf.repository.ItemRepository
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ItemViewModel
    private lateinit var myAdapter: MyAdapter
    private val lst= listOf(
        Item("abv","adkdfkd"),
        Item("ab","adkdfkd"),
        Item("abs","adkdfkd"),
        Item("arv","adkdfkd"),
        Item("gbv","adkdfkd"),
        Item("hbv","adkdfkd"),
        Item("nbv","adkdfkd"),
        Item("auv","adkdfkd"),
        Item("awv","adkdfkd"),
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myAdapter=MyAdapter(lst)
        setUpAdapter()
        val databaseReference = FirebaseDatabase.getInstance().getReference("items")
        val repository = ItemRepository(databaseReference)
        viewModel = ViewModelProvider(
            this,
            ItemViewModelFactory(Application(), repository)
        )[ItemViewModel::class.java]
        // Example: Add item on button click
        binding.addButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val body = binding.bodyEditText.text.toString()
            if (title.isNotBlank() && body.isNotBlank()) {
                viewModel.addItem(title, body)
            }
        }

        // Example: Delete checked items on another button click
        binding.deleteButton.setOnClickListener {
            viewModel.deleteCheckedItems()
        }

        // Observe the LiveData and update UI accordingly
        viewModel.items.observe(this, Observer { items ->
            // Update your RecyclerView or other UI components
            myAdapter.updateData(items)
            // Ensure the adapter is set up after updating the data
            setUpAdapter()

        })

    }
    private fun setUpAdapter() {
        if (!::myAdapter.isInitialized) {
            // Initialize the adapter only if not already initialized
            myAdapter = MyAdapter(emptyList()) // Pass an empty list initially
            binding.recyclerView.adapter = myAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}