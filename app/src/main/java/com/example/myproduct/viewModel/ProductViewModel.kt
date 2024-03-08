package com.example.myproduct.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproduct.model.Product
import com.example.myproduct.model.ProductX
import com.example.myproduct.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel (): ViewModel() {

    //fetching all the data
    private lateinit var  productLiveData:LiveData<List<Product>>
    fun getAllProducts():LiveData<List<Product>>{
        viewModelScope.launch {
          productLiveData =  ProductRepository().getMutableLiveData()
        }
        return productLiveData
    }

//fetching the product details based on the id
   private lateinit var productDetailLiveData: LiveData<List<ProductX>>
   fun getProductDetailsById(product_id:Int):LiveData<List<ProductX>>{
       viewModelScope.launch() {
           productDetailLiveData= ProductRepository().getProductDetailsById(product_id)
       }

       return productDetailLiveData
   }
}