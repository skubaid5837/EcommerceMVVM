package com.example.ecommerce.di

import android.content.Context
import com.example.ecommerce.room.CartDAO
import com.example.ecommerce.room.CartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CartDatabase {
        return CartDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDAO(database: CartDatabase): CartDAO {
        return database.cartDao()
    }

}