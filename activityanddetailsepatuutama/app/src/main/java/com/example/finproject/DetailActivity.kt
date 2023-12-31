package com.example.finproject

import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finproject.model.Produk

class DetailActivity : AppCompatActivity() {

    private lateinit var ivProduct: ImageView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductDetail: TextView
    private lateinit var svProduct: ScrollView
    private lateinit var product: Produk

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail) // Menggunakan layout activity_detail.xml
        ivProduct = findViewById(R.id.iv_product_detail)
        tvProductName = findViewById(R.id.tv_product_name)
        tvProductDetail = findViewById(R.id.tv_product_detail)
        //svProduct = findViewById(R.id.sv_product)
        product = intent.getParcelableExtra("product") ?: Produk("", null, "") // Menerima objek Produk dari Intent
        ivProduct.setImageBitmap(product.image)
        tvProductName.text = product.name
        tvProductDetail.text = product.detail
        svProduct.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val alpha = 1 - scrollY.toFloat() / ivProduct.height
            ivProduct.alpha = alpha
        }
    }

    override fun onResume() {
        super.onResume()
        // Lakukan operasi yang diperlukan saat Activity menjadi aktif
    }

    override fun onPause() {
        super.onPause()
        // Lakukan operasi yang diperlukan saat Activity menjadi tidak aktif
    }
}
