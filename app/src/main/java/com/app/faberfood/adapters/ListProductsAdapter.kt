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

class ListProductsAdapter(private var products: List<Product>, context: Context) :
    RecyclerView.Adapter<ListProductsAdapter.ProductsViewHolder>() {

    private val dbShoppingCart: ShoppingCartDataSource = ShoppingCartDataSource(context)

    fun refreshData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
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
        holder.bindView(product)

        holder.addQuantityProductButton.setOnClickListener {
            dbShoppingCart.addProductToShoppingCart(product)
            Toast.makeText(holder.itemView.context, "Product added", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ProductsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProduct)
        val nameProduct: TextView = itemView.findViewById(R.id.titleItemProduct)
        val descriptionProduct: TextView = itemView.findViewById(R.id.descriptionProduct)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProduct)
        val addQuantityProductButton: ImageView = itemView.findViewById(R.id.addProductButton)


        fun bindView(product: Product){
            imageProduct.setImageResource(product.image)
            nameProduct.text = product.name
            descriptionProduct.text = product.description
            priceProduct.text = "$${product.price}"
        }
    }
}