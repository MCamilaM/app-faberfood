package com.app.faberfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faberfood.adapters.ShoppingCartAdapter
import com.app.faberfood.databinding.ActivityShoppingCartBinding
import com.app.faberfood.db.ShoppingCartDataSource
import com.app.faberfood.entities.ItemShoppingCart

class ShoppingCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var shoppingCartDataSource: ShoppingCartDataSource
    private lateinit var shoppingCartAdapter: ShoppingCartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shoppingCartDataSource = ShoppingCartDataSource(this)

        val products = shoppingCartDataSource.getAllProducts()
        this.recyclerView = findViewById(R.id.productsCartRecyclerView)
        this.emptyTextView = findViewById(R.id.textEmptyShoppingCart)

        changeVisibilityTextEmptyCart(products.size)

        shoppingCartAdapter =
            ShoppingCartAdapter(products, this)

        binding.productsCartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsCartRecyclerView.adapter = shoppingCartAdapter

        updateTotalPrice(products)

        binding.backArrowCart.setOnClickListener {
            finish()
        }
    }

    override fun onResume(){
        super.onResume()
        shoppingCartAdapter.refreshData(shoppingCartDataSource.getAllProducts())
    }

    fun updateTotalPrice(products: List<ItemShoppingCart>) {
        var subTotal = 0.0
        var shippingPrice = 0.0
        var totalPrice = "0.0"

        if(products.isNotEmpty()){
            subTotal = String.format("%.2f", shoppingCartDataSource.getSubTotal(products)).toDouble()
            shippingPrice = 4.99
            totalPrice = String.format("%.2f", subTotal.plus(shippingPrice))
        }else{
            changeVisibilityTextEmptyCart(0)
        }

        binding.subTotalPriceCart.text = "$${subTotal}"
        binding.shippingPriceCart.text = "$${shippingPrice}"
        binding.totalPriceCart.text = "$${totalPrice}"
    }

    fun changeVisibilityTextEmptyCart(sizeProducts: Int){
        if (sizeProducts == 0) {
            emptyTextView.visibility = View.VISIBLE // Mostrar el mensaje si no hay datos
            recyclerView.visibility = View.GONE // Ocultar el RecyclerView
        } else {
            emptyTextView.visibility = View.GONE // Ocultar el mensaje si hay datos
            recyclerView.visibility = View.VISIBLE // Mostrar el RecyclerView
        }
    }

}