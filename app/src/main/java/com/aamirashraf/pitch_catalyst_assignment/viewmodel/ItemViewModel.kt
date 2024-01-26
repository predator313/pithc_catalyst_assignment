package com.aamirashraf.pitch_catalyst_assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aamirashraf.model.Item
import com.aamirashraf.repository.ItemRepository

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

//    private val _items = MutableLiveData<List<Item>>()
//    val items: LiveData<List<Item>> get() = _items
//
//    fun addItem(title: String, body: String) {
//        repository.addItem(Item(title, body))
//    }
//
//    fun deleteCheckedItems() {
//        repository.deleteCheckedItems()
//    }

    // You may also need to fetch initial data from Firebase
    // and update _items LiveData accordingly.

    val items: LiveData<List<Item>> = repository.items

    fun addItem(title: String, body: String) {
        repository.addItem(Item(title, body))
    }

    fun deleteCheckedItems() {
        repository.deleteCheckedItems()
    }
}