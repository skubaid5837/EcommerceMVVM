package com.example.ecommerce.views

import android.content.Intent
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.ActivityCategoryItemsBinding
import com.example.ecommerce.util.CategoryAdapter
import com.example.ecommerce.util.ProductAdapter
import com.example.ecommerce.viewmodel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryItems : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryItemsBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RecyclerView
        productAdapter = ProductAdapter { product ->
            val intent = Intent(this, ProductDetail::class.java)
            intent.putExtra("product_id", product.id)
            intent.putExtra("product_title", product.title)
            intent.putExtra("product_price", product.price.toString())
            intent.putExtra("product_image_url", product.imageUrl)
            startActivity(intent)
        }

        binding.recyclerViewCategory.adapter = productAdapter
        binding.recyclerViewCategory.layoutManager = LinearLayoutManager(this)

        //getClicked Category Name
        val categoryName = intent.getStringExtra("Category_name") ?: ""

        val result = viewModel.fetchProducts(categoryName)
        result.observe(this){
            newProducts ->
            if (newProducts.isNotEmpty()){
                productAdapter.submitList(newProducts)
            }
        }
    }
}