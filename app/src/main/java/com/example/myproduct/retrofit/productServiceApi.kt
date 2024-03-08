package com.example.myproduct.retrofit

import com.example.myproduct.model.Product
import com.example.myproduct.model.ProductX
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface productServiceApi {

    @GET("/products")
     fun getAllProducts(): Call<Product>


     @GET("/products/{productId}")
     fun getProductById(@Path("productId") productId:Int) : Call<ProductX>

}