package com.example.ecommerce.views

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityProductDetailBinding
import com.example.ecommerce.models.Product
import com.example.ecommerce.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetail : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val viewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId = intent.getStringExtra("product_id") ?: "0"
        val productTitle = intent.getStringExtra("product_title") ?: ""
        val productPrice = intent.getStringExtra("product_price") ?: "0.0"
        val productImageUrl = intent.getStringExtra("product_image_url") ?: ""

        //display the data
        binding.productTitleDetail.text = productTitle
        binding.productPriceDetail.text = productPrice
        Glide.with(this)
            .load(productImageUrl)
            .into(binding.productImageDetail)

        //addToCart
        binding.addToCartButton.setOnClickListener {
            addToCart(Product(productId, productTitle, productPrice.toDouble(), productImageUrl))
            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToCart(product: Product) {
        viewModel.addToCart(product)
    }
}