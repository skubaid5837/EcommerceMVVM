package com.example.ecommerce.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCartBinding
import com.example.ecommerce.databinding.ItemCategoryBinding
import com.example.ecommerce.models.Product

class CartAdapter(
    private val onRemoveFromCartClicked: (Product) -> Unit
): ListAdapter<Product, CartAdapter.CartViewHolder>(ProductDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int
    ) {
        val product = getItem(position)
        holder.binding.cartItemPrice.text = product.price.toString()
        holder.binding.cartItemTitle.text = product.title

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .into(holder.binding.cartItemImage)

        holder.binding.removeCartItemButton.setOnClickListener {
            onRemoveFromCartClicked(product)
        }
    }

    class CartViewHolder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root){

    }
}