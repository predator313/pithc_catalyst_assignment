package com.aamirashraf.repository

import android.util.Log
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
                withContext(Dispatchers.Main){
                    Log.d("hello","successfully saved the data")
                }

            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    Log.d("hello",e.message.toString())
                }
            }
        }

    }


    suspend fun getAllItems(): List<Item> = withContext(Dispatchers.IO) {
        try {
            val querySnapshot = databaseReference.get().await()
            Log.d("aamir", querySnapshot.size().toString())
            val list= mutableListOf<Item>()
            val sbuilder=StringBuilder()
            for(item in querySnapshot.documents){
               val person=item.toObject<Item>()
//                sbuilder.append(person)
                if(person!=null)
                  list.add(person)
            }
            withContext(Dispatchers.Main){
//                Log.d("aamir",item.toString()+"for loop")
                Log.d("aamir",sbuilder.toString()+"for loop")
            }
//            return@withContext querySnapshot.toObjects(Item::class.java)
            return@withContext list
        } catch (e: Exception) {
            Log.d("hello", e.message.toString()+"get all exception")
            return@withContext emptyList()
        }
    }





}
