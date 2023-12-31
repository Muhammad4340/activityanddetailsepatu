package com.example.finproject

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finproject.model.Produk

class VarianProductAdapter (private val list: ArrayList<Produk>) : RecyclerView.Adapter<VarianProductAdapter.MenuViewHolder>(){

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.card_layout_varian, parent, false)

        return MenuViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateProducts(products: List<Produk>) {
        TODO("Not yet implemented")
    }

    inner class MenuViewHolder(v: View):RecyclerView.ViewHolder(v) {
        fun bind(produk: Produk) {
            TODO("Not yet implemented")
        }

        val imageViewProduk: ImageView
        val textViewNamaProduk: TextView


        init {
            imageViewProduk = v.findViewById(R.id.iv_produk)
            textViewNamaProduk = v.findViewById(R.id.tv_namaproduk)

            fun bind(data: Produk) {
                val name: String = data.name
                val gambar: Bitmap? = data.image
                val detail: String = data.detail


                textViewNamaProduk.text = name.toString()
                imageViewProduk.setImageBitmap(gambar)
            }
        }

    }

    companion object {
        fun updateProducts(products: List<Produk>) {

        }
    }
}


