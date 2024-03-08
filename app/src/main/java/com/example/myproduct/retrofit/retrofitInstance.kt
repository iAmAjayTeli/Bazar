package com.example.myproduct.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class retrofitInstance {

    companion object {
        val baseUrl = "https://dummyjson.com"

        fun getRetrofitInstance():Retrofit{
           return Retrofit.Builder()
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
        }


    }




}