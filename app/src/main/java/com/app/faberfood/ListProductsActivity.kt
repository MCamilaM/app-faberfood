package com.app.faberfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.faberfood.adapters.ListProductsAdapter
import com.app.faberfood.databinding.ActivityListProductsBinding
import com.app.faberfood.db.DBHelper
import com.app.faberfood.db.ProductDataSource
import com.app.faberfood.db.ShoppingCartDataSource
import com.app.faberfood.entities.Product

class ListProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var productsDataSource: ProductDataSource
    private lateinit var listProductsAdapter: ListProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_list_products)

        productsDataSource = ProductDataSource(this)

        insertProducts()

        listProductsAdapter = ListProductsAdapter(productsDataSource.getAllProducts(), this)

        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = listProductsAdapter

    }

    fun insertProducts() {
        val product1 =
            Product(
                id = 1,
                name = "Teléfono inteligente",
                description = "Un teléfono inteligente de última generación",
                price = 599.99,
                category = "Electrónicos"
            )
        val product2 = Product(
            id = 2,
            name = "Laptop",
            description = "Una potente laptop para trabajo y entretenimiento",
            price = 1299.99,
            category = "Electrónicos"
        )
        val product3 = Product(
            id = 3,
            name = "Auriculares inalámbricos",
            description = "Auriculares Bluetooth con cancelación de ruido",
            price = 129.99,
            category = "Electrónicos"
        )
        productsDataSource.insertProduct(product1)
        productsDataSource.insertProduct(product2)
        productsDataSource.insertProduct(product3)
    }
}