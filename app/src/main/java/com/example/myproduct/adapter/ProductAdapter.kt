package com.example.myproduct.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myproduct.databinding.ProductSampleBinding
import com.example.myproduct.model.Product
import com.example.myproduct.model.ProductX
import com.example.myproduct.view.DetailActivity

class ProductAdapter(val productList:ArrayList<ProductX>) : RecyclerView.Adapter<ProductAdapter.viewHolder>() {



    inner class viewHolder( val binding: ProductSampleBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       return viewHolder(ProductSampleBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
      val product:ProductX= productList[position]
       holder.binding.product= product

        holder.binding.root.setOnClickListener {
           val clickedItemId= product.id
            val intent=Intent(it.context, DetailActivity::class.java)
               intent.putExtra("Product_id", clickedItemId)

           it.context.startActivity(intent)
        }




    }
}