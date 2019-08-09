package com.test.firebaseshop.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.firebaseshop.data.ItemRepository
import com.test.firebaseshop.model.Item

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var itemRepository: ItemRepository
    init {
        itemRepository = ItemRepository(application)
    }

    fun getItems(): LiveData<List<Item>> {
        return itemRepository.getAllItems()
    }

    fun setCategory(categoryId: String) {
        itemRepository.setCategory(categoryId)
    }
}