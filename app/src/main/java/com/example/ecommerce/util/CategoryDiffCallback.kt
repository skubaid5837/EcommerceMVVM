package com.example.ecommerce.util

import androidx.recyclerview.widget.DiffUtil
import com.example.ecommerce.models.Category

class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem == newItem
    }
}