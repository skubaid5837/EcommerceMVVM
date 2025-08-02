package com.example.ecommerce.repository

import androidx.core.os.registerForAllProfilingResults
import androidx.lifecycle.MutableLiveData
import com.example.ecommerce.R
import com.example.ecommerce.models.Category
import com.example.ecommerce.models.Product
import com.example.ecommerce.room.CartDAO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repository @Inject constructor(private val firestore: FirebaseFirestore, private val cartDAO: CartDAO){

    fun fetchCategory(): MutableLiveData<List<Category>>{
        val categoriesList = MutableLiveData<List<Category>>()

        val catImages = mapOf(
            "Electronics" to R.drawable.electronics,
            "Jewelry" to R.drawable.jewelery,
            "MensClothing" to R.drawable.mensclothing,
            "WomensClothing" to R.drawable.womenclothing
        )

        firestore.collection("categories")
            .get()
            .addOnSuccessListener { documents ->
                val category = documents.map { document ->
                    val imageRes = catImages[document.id] ?: R.drawable.ic_launcher_background
                    Category(name = document.id, catImg = imageRes)
                }
                categoriesList.postValue(category)
            }
        return categoriesList
    }

    fun fetchProduct(categoryName: String): MutableLiveData<List<Product>>{
        val productList = MutableLiveData<List<Product>>()

        firestore.collection("categories")
            .document(categoryName)
            .collection("products")
            .get()
            .addOnSuccessListener { documentSnapshots ->
                val product = documentSnapshots.map {
                    document ->
                    Product(id = document.getString("id") ?: "",
                        title = document.getString("title") ?: "",
                        price = document.getDouble("price") ?: 0.0,
                        imageUrl = document.getString("imageUrl") ?: ""
                    )
                }
                productList.postValue(product)
            }
        return productList
    }

    fun uploadPurchaseItem(product: Product){
        firestore.collection("orders")
            .add(product)
            .addOnSuccessListener {
                CoroutineScope(Dispatchers.IO).launch {
                    clearCart()
                }
            }
    }

    //room function

    suspend fun addToCart(product: Product){
        cartDAO.addToCart(product)
    }

    suspend fun getCartItems(): List<Product>{
        return cartDAO.getCartItems()
    }

    suspend fun removeFromCart(productId: String){
        cartDAO.removeFromCart(productId)
    }

    suspend fun clearCart(){
        cartDAO.clearCart()
    }
}