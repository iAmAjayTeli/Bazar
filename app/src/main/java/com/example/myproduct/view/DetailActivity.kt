package com.example.myproduct.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myproduct.R
import com.example.myproduct.adapter.ImageSlideAdapter
import com.example.myproduct.databinding.ActivityDetailBinding
import com.example.myproduct.viewModel.ProductViewModel
import me.relex.circleindicator.CircleIndicator

class DetailActivity : AppCompatActivity() {

    lateinit var viewModel: ProductViewModel
    lateinit var binding: ActivityDetailBinding

    //images slider
//    private var imagesModel:ImagesModel? = null
    lateinit var viewPagerAdapter: ImageSlideAdapter
    lateinit var indicator: CircleIndicator
    lateinit var viewpager: ViewPager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)


        viewpager=binding.viewpager

        val product_id = intent.getIntExtra("Product_id", -1)

        if (product_id != -1) {
            updateDetailActivity(product_id)
        }


    }

    private fun updateDetailActivity(productId: Int) {
      viewModel.getProductDetailsById(productId).observe(this, Observer { productList->
          if(productList!=null){
              binding.productDetails= productList[0]
              val imageList:List<String> = productList[0].images

              imageList.let {
                  viewPagerAdapter = ImageSlideAdapter(this, it)
                  viewpager.adapter = viewPagerAdapter
                  indicator = this.findViewById<CircleIndicator>(R.id.indicator)!!
                  indicator.setViewPager(viewpager)
              }


          }
      })
    }
}