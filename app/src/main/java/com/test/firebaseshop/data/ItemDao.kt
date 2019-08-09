package com.test.firebaseshop.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.firebaseshop.model.Item

@Dao
interface ItemDao {
    @Query("select * from Item order by viewCount")
    fun getItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: Item)
}