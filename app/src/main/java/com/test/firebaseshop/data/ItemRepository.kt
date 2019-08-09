package com.test.firebaseshop.data

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import com.test.firebaseshop.model.Item
import com.test.firebaseshop.view.FirestoreQueryLiveData

class ItemRepository(application: Application) {
    private var itemDao: ItemDao
    private lateinit var items: LiveData<List<Item>>
    private var firestoreQueryLiveData = FirestoreQueryLiveData()
    private var network = true

    init {
        itemDao = ItemDatabase.getDatabase(application).getItemDao()
        items = itemDao.getItems()
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        network = networkInfo.isConnected
    }

    fun getAllItems(): LiveData<List<Item>> {
        if (network) {
            items = firestoreQueryLiveData
        } else {
            items = itemDao.getItems()
        }
        return items
    }

    fun setCategory(categoryId: String) {
        if (network) {
            firestoreQueryLiveData.setCategory(categoryId)
        } else {
            items = itemDao.getItemsByCategory(categoryId)
        }

    }
}