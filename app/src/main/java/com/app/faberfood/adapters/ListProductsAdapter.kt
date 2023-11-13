package com.app.faberfood.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.faberfood.R
import com.app.faberfood.ShoppingCartActivity
import com.app.faberfood.db.ShoppingCartDataSource
import com.app.faberfood.entities.Product
import com.app.faberfood.entities.User

class ListProductsAdapter(private var products: List<Product>, context: Context) :
    RecyclerView.Adapter<ListProductsAdapter.ProductsViewHolder>() {

    private val dbShoppingCart: ShoppingCartDataSource = ShoppingCartDataSource(context)

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val nameProduct: TextView = itemView.findViewById(R.id.titleItemProduct)
        val descriptionProduct: TextView = itemView.findViewById(R.id.descriptionProduct)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProduct)
        val addQuantityProductButton: ImageView = itemView.findViewById(R.id.addProductButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = products[position]
        holder.nameProduct.text = product.name
        holder.descriptionProduct.text = product.description
        holder.priceProduct.text = "$${product.price}"

        holder.addQuantityProductButton.setOnClickListener {
            val user = User(1,"prueba", "app", "prueba@gmail.com", "12345")
            dbShoppingCart.addProductToShoppingCart(user, product)
            Toast.makeText(holder.itemView.context, "Product added", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun refreshData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }
}