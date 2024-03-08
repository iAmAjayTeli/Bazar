package com.example.myproduct.view

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.myproduct.R
import com.example.myproduct.adapter.ProductAdapter
import com.example.myproduct.databinding.ActivityMainBinding
import com.example.myproduct.model.ProductX
import com.example.myproduct.repository.ProductRepository
import com.example.myproduct.viewModel.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity () : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var productList:ArrayList<ProductX>
    lateinit var recyclerView: RecyclerView
    lateinit var loadingAnimation: LottieAnimationView
    lateinit var productAdapter: ProductAdapter
    lateinit var viewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        loadingAnimation=binding.loading
        recyclerView= binding.recyclerview

        loadingAnimation.visibility=View.VISIBLE



        //initializing the viewModel
        viewModel=ViewModelProvider(this).get(ProductViewModel::class.java)


        // Check for network connectivity before making the network request
        if (isNetworkAvailable()) {
            getProductList()
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }




}

    private fun getProductList() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getAllProducts().observe(this@MainActivity, Observer {
                      productList= it as ArrayList<ProductX>
                displayProductInRecyclerview()
            })
        }
        }

    private fun displayProductInRecyclerview() {
        loadingAnimation.visibility=View.GONE
        recyclerView.visibility=View.VISIBLE
        productAdapter= ProductAdapter(productList)

        recyclerView.adapter= productAdapter
        recyclerView.layoutManager=GridLayoutManager(this,2)


        productAdapter.notifyDataSetChanged()

    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}
