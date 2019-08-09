package com.test.firebaseshop

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun getItemDao(): ItemDao

    companion object {
        var INSTANCE: ItemDatabase? = null
        fun getDatabase(context: Context): ItemDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, ItemDatabase::class.java, "mydb")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE
        }
    }
}