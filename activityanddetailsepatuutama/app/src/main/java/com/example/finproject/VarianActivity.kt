package com.example.finproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finproject.model.Produk

class VarianActivity : AppCompatActivity() {
    private lateinit var rvVarian: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varian)

        // Inisialisasi RecyclerView dan adapter
        rvVarian = findViewById(R.id.rv_varian)
        val databaseHelper = DatabaseHelper(this)
        val listData = databaseHelper.getAllData()
        rvVarian.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvVarian.adapter = VarianProductAdapter(listData)
    }

    // Menangani hasil dari pengambilan gambar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageUri = data?.data
            imageUri?.let {
                saveImageToDatabase(it)
            }
        }
    }

    // Implementasi pengambilan data produk dari database
    private fun getProductsFromDatabase(): List<Produk> {
        val dbHelper = DatabaseHelper(this)
        return dbHelper.getAllProducts()
    }

    // Fungsi untuk membuka kamera
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
    }

    // Fungsi untuk menyimpan gambar ke database
    private fun saveImageToDatabase(uri: Uri) {
        val dbHelper = DatabaseHelper(this)
        val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val newProduct = Produk("New Product", imageBitmap, "Product Details")
        val isInserted = dbHelper.insertProduct(newProduct)
        if (isInserted) {
            var products = getProductsFromDatabase()
            VarianProductAdapter.updateProducts(products)
        }
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}