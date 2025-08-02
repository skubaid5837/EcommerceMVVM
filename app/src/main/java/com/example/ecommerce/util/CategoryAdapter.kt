package com.example.ecommerce.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCategoryBinding
import com.example.ecommerce.models.Category

class CategoryAdapter(
    private val onCategoryClicked: (String) -> Unit
): ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val category = getItem(position)
        holder.binding.categoryName.text = category.name

        Glide.with(holder.itemView.context)
            .load(category.catImg)
            .into(holder.categoryImg)

        holder.itemView.setOnClickListener {
            onCategoryClicked(category.name)
        }
    }

    class CategoryViewHolder(val binding: ItemCategoryBinding): RecyclerView.ViewHolder(binding.root){
        val categoryImg = binding.imageViewCategory
    }


}