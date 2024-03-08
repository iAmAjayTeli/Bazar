package com.example.myproduct.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myproduct.R

class bindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("thumbnail")
        fun loadImage(view: ImageView, thumbnail: String?) {
            thumbnail?.let {
                Glide.with(view.context)
                    .load(it)
                    .placeholder(R.drawable.placeholder)
                    .into(view)
            }
        }

    }}