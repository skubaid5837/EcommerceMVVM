package com.example.ecommerce.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.models.Category
import com.example.ecommerce.models.Product
import com.example.ecommerce.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val repository: Repository): ViewModel(){

    fun fetchCategories(): MutableLiveData<List<Category>>{
        return repository.fetchCategory()
    }

    fun fetchProducts(categoryName: String) : MutableLiveData<List<Product>>{
        return repository.fetchProduct(categoryName)
    }

    fun addToCart(product: Product) = viewModelScope.launch {
        repository.addToCart(product)
    }

    fun getCartItem(): MutableLiveData<List<Product>>{
        val cartItems = MutableLiveData<List<Product>>()
        viewModelScope.launch {
            val items = repository.getCartItems()
            cartItems.postValue(items)
        }
        return cartItems
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    fun removeCartItem(productId: String) = viewModelScope.launch {
        repository.removeFromCart(productId)
    }

    fun uploadPurchaseItem(product: Product){
        repository.uploadPurchaseItem(product)
    }
}