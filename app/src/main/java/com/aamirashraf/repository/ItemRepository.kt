package com.aamirashraf.repository

import androidx.lifecycle.LiveData
import com.aamirashraf.model.Item
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseReference
import androidx.lifecycle.MutableLiveData


class ItemRepository(private val databaseReference: DatabaseReference) {

//    fun addItem(item: Item) {
//        val newItemReference = databaseReference.push()
//        newItemReference.setValue(item)
//    }
//
//    fun deleteCheckedItems() {
//        val query = databaseReference.orderByChild("isChecked").equalTo(true)
//
//        query.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (itemSnapshot in snapshot.children) {
//                    itemSnapshot.ref.removeValue()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error
//            }
//        })
//    }

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items

    init {
        // Fetch initial data from Firebase and update _items
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<Item>()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    item?.let {
                        itemList.add(it)
                    }
                }
                _items.value = itemList
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    fun addItem(item: Item) {
        val newItemReference = databaseReference.push()
        newItemReference.setValue(item)
    }

    fun deleteCheckedItems() {
        val query = databaseReference.orderByChild("isChecked").equalTo(true)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (itemSnapshot in snapshot.children) {
                    itemSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}
