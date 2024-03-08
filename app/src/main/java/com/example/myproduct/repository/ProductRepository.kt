package com.example.myproduct.repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myproduct.model.Product
import com.example.myproduct.model.ProductX
import com.example.myproduct.retrofit.productServiceApi
import com.example.myproduct.retrofit.retrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository() {

    private var productList = ArrayList<Product>()
    private var mutableLiveData = MutableLiveData<List<Product>>()
    lateinit var serviceApi: productServiceApi

//getting the all the data
    suspend fun getMutableLiveData(): MutableLiveData<List<Product>> {
         serviceApi = retrofitInstance.getRetrofitInstance().create(productServiceApi::class.java)

        val call: Call<Product> = serviceApi.getAllProducts()

        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                //if the request is successfully
                val product = response.body()!!
                if (product?.products != null) {

                    productList = product.products as ArrayList<Product>
                    mutableLiveData.value = productList

                }


            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return mutableLiveData
    }


   //fetching the data based on the id

    private var productDetails= ArrayList<ProductX>()
    private  var productDetailsMutableLiveData= MutableLiveData<List<ProductX>>()

    suspend fun getProductDetailsById(product_id: Int): MutableLiveData<List<ProductX>> {
        val serviceApi = retrofitInstance.getRetrofitInstance().create(productServiceApi::class.java)

        val call: Call<ProductX> = serviceApi.getProductById(product_id)

        call.enqueue(object : Callback<ProductX> {
            override fun onResponse(call: Call<ProductX>, response: Response<ProductX>) {
                if (response.isSuccessful) {
                    val productX = response.body()
                    productX?.let {
                        productDetails.add(it)
                        productDetailsMutableLiveData.postValue(productDetails)
                    }
                } else {
                    // Handle unsuccessful response (optional)
                }
            }

            override fun onFailure(call: Call<ProductX>, t: Throwable) {
                // Handle failure (optional)
            }
        })

        return productDetailsMutableLiveData
    }


}
