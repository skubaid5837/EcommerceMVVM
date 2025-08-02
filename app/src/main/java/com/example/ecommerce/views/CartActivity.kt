package com.example.ecommerce.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityCartBinding
import com.example.ecommerce.models.Product
import com.example.ecommerce.util.CartAdapter
import com.example.ecommerce.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartAdapter = CartAdapter { cartItem ->
            removeCartItem(cartItem)
        }

        binding.recyclerViewCart.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCart.adapter = cartAdapter

        binding.clearCartButton.setOnClickListener {
            viewModel.clearCart()
            val updatedCartItems = viewModel.getCartItem().value?.toMutableList()
            cartAdapter.submitList(updatedCartItems)
        }

        binding.checkOutButton.setOnClickListener {
            checkoutBtn()
            Toast.makeText(this, "Purchased", Toast.LENGTH_SHORT).show()
        }

        fetchCartItem()
    }

    private fun removeCartItem(cartItem: Product){
        viewModel.removeCartItem(cartItem.id)
        val updatedCartItems = viewModel.getCartItem().value?.toMutableList()
        cartAdapter.submitList(updatedCartItems)
    }

    private fun fetchCartItem() {
        viewModel.getCartItem().observe(this){cartItems ->
            cartAdapter.submitList(cartItems)
        }
    }

    private fun checkoutBtn(){
        viewModel.getCartItem().observe(this){ purchaseItem ->
            for (item in purchaseItem) {
                viewModel.uploadPurchaseItem(item)
            }
        }
        cartAdapter.submitList(emptyList())
    }
}