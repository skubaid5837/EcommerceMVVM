package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.ActivityMainBinding
import com.example.ecommerce.util.CategoryAdapter
import com.example.ecommerce.viewmodel.MyViewModel
import com.example.ecommerce.views.CartActivity
import com.example.ecommerce.views.CategoryItems
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set up recyclerView
        categoryAdapter = CategoryAdapter { categoryName ->
            val intent = Intent(this, CategoryItems::class.java)
            intent.putExtra("Category_name", categoryName)
            startActivity(intent)
        }

        binding.recyclerView.adapter = categoryAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        val result = viewModel.fetchCategories()
        result.observe(this){
            categoryList ->
            if (categoryList.isNotEmpty()){
                categoryAdapter.submitList(categoryList)
            }else{
                Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.viewCartButton.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}