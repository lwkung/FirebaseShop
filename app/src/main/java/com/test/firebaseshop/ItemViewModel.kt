package com.test.firebaseshop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ItemViewModel : ViewModel() {

    private var items = MutableLiveData<List<Item>>()
    private var firestoreQueryLiveData = FirestoreQueryLiveData()

    fun getItems(): FirestoreQueryLiveData {
        return firestoreQueryLiveData
    }

    fun setCategory(categoryId: String) {
        firestoreQueryLiveData.setCategory(categoryId)
    }
}