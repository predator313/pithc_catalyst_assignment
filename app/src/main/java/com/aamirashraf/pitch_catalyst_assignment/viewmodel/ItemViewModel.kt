package com.aamirashraf.pitch_catalyst_assignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamirashraf.model.Item
import com.aamirashraf.repository.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {


    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    init {
        // Call the function to retrieve all items and update the LiveData

        viewModelScope.launch {
            _items.value = repository.getAllItems()
            Log.d("aamir",repository.getAllItems().size.toString()+"viewmodel scope")
        }
    }

    fun addItem(title: String, body: String) {
        viewModelScope.launch {
            repository.addItem(Item(title, body))
            // Update the LiveData after adding the item
            _items.value = repository.getAllItems()
        }
    }
    fun updateCheckedStatus(item: Item) {
        viewModelScope.launch {
            repository.updateCheckedStatus(item)
        }
    }

    fun deleteItems() {
        viewModelScope.launch {
            repository.deleteItems()
            // Update the LiveData after deleting the items
            _items.value = repository.getAllItems()
        }
    }


}