package com.example.ecommerce.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerce.models.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class CartDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDAO

    companion object{
        @Volatile
        private var INSTANCE: CartDatabase ?= null

        fun getDatabase(context: Context): CartDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}