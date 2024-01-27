package com.aamirashraf.repository

import android.util.Log
import android.widget.Toast
import com.aamirashraf.model.Item

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.StringBuilder


class ItemRepository(private val databaseReference: CollectionReference) {

    fun addItem(item: Item) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                databaseReference.add(item).await()
                withContext(Dispatchers.Main) {
                    Log.d("hello", "successfully saved the data")
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("hello", e.message.toString())
                }
            }
        }

    }


    suspend fun getAllItems(): List<Item> = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = databaseReference.get().await()
            Log.d("aamir", querySnapshot.size().toString())
            val list = mutableListOf<Item>()
            val sbuilder = StringBuilder()
            for (item in querySnapshot.documents) {
                val person = item.toObject<Item>()
//                sbuilder.append(person)
                if (person != null)
                    list.add(person)
            }
            withContext(Dispatchers.Main) {
//                Log.d("aamir",item.toString()+"for loop")
                Log.d("aamir", sbuilder.toString() + "for loop")
            }
//            return@withContext querySnapshot.toObjects(Item::class.java)
            return@withContext list
        } catch (e: Exception) {
            Log.d("hello", e.message.toString() + "get all exception")
            return@withContext emptyList()
        }
    }

    //here we delete the check items

    suspend fun deleteItems() = withContext(Dispatchers.IO) {
        val itemQuery = databaseReference.whereEqualTo("checked", true)
            .get()
            .await()
        if (itemQuery.documents.isNotEmpty()) {
            for (document in itemQuery) {
                try {
                    databaseReference.document(document.id).delete().await()
                    withContext(Dispatchers.Main) {
                        Log.d("hello", "Successfully deleted the selected items")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("hello", e.message.toString())
                    }

                }
            }
        }
    }
    suspend fun updateCheckedStatus(item: Item) = withContext(Dispatchers.IO) {
        val itemQuery=databaseReference
            .whereEqualTo("title",item.title)
            .whereEqualTo("body",item.body)
            .get()
            .await()
        if(itemQuery.documents.isNotEmpty()){
            for(document in itemQuery){
                try {
                    databaseReference.document(document.id).update("checked", item.isChecked).await()
                    withContext(Dispatchers.Main) {
                        Log.d("hello", "Successfully updated checked status in Firebase")
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("hello", e.message.toString())
                    }
                }
            }

        }


    }
    fun realTimeUpdates(){
        databaseReference.addSnapshotListener { value, error ->
            error?.let {
                //means there is firebase exception
                Log.d("aamir",it.message.toString()+" firebase exception")
                return@addSnapshotListener  //means we are out of the snapshot listener
            }
            value?.let {
                for(document in value.documents){
                    val item=document.toObject<Item>()
                }
            }

        }
    }


}
