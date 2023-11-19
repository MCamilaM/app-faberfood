package com.app.faberfood.adapters

import android.content.Context
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
import com.app.faberfood.entities.ItemShoppingCart

class ShoppingCartAdapter(private var products: List<ItemShoppingCart>, private var context: Context) :
    RecyclerView.Adapter<ShoppingCartAdapter.ShopingCartViewHolder>() {

    private val dbShoppingCart: ShoppingCartDataSource = ShoppingCartDataSource(context)

    fun refreshData(newProductsCart: List<ItemShoppingCart>) {
        products = newProductsCart
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopingCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return ShopingCartViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShopingCartViewHolder,
        position: Int
    ) {
        val item = products[position]
        holder.bindView(item)

        holder.addQuantityProductButton.setOnClickListener {
            dbShoppingCart.updateProductQuantity(item.id, item.quantity + 1)
            refreshData(dbShoppingCart.getAllProducts())
            (context as ShoppingCartActivity).updateTotalPrice(products)
            Toast.makeText(holder.itemView.context, "${item.product.name} +1", Toast.LENGTH_SHORT).show()
        }

        holder.removeQuantityProductButton.setOnClickListener {
            var text = ""
            if(item.quantity == 1){
                text = "Product deleted"
                dbShoppingCart.deleteProduct(item.id)
            }else{
                text = "${item.product.name} -1"
                dbShoppingCart.updateProductQuantity(item.id, item.quantity - 1)
            }
            val products = dbShoppingCart.getAllProducts()
            refreshData(products)
            (context as ShoppingCartActivity).updateTotalPrice(products)
            Toast.makeText(holder.itemView.context, text, Toast.LENGTH_SHORT).show()
        }

        holder.deleteProductButton.setOnClickListener {
            dbShoppingCart.deleteProduct(item.id)
            val products = dbShoppingCart.getAllProducts()
            refreshData(products)
            (context as ShoppingCartActivity).updateTotalPrice(products)
            Toast.makeText(holder.itemView.context, "${item.product.name} deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
    class ShopingCartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageProduct: ImageView = itemView.findViewById(R.id.imageProductCart)
        val nameProduct: TextView = itemView.findViewById(R.id.titleProductCart)
        val priceProduct: TextView = itemView.findViewById(R.id.priceProductCart)
        val quantityProduct: TextView = itemView.findViewById(R.id.quantityItemText)
        val addQuantityProductButton: ImageView = itemView.findViewById(R.id.addQuantityCartButton)
        val removeQuantityProductButton: ImageView =
            itemView.findViewById(R.id.removeQuantityCartButton)
        val deleteProductButton: ImageView = itemView.findViewById(R.id.deleteProductCartButton)

        fun bindView(item: ItemShoppingCart) {
            imageProduct.setImageResource(item.product.image)
            nameProduct.text = item.product.name
            priceProduct.text = "$${item.product.price}"
            quantityProduct.text = item.quantity.toString()
        }
    }

}