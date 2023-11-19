package com.app.faberfood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.faberfood.adapters.ListProductsAdapter
import com.app.faberfood.databinding.ActivityListProductsBinding
import com.app.faberfood.db.ProductDataSource

class ListProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductsBinding
    private lateinit var productsDataSource: ProductDataSource
    private lateinit var listProductsAdapter: ListProductsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        productsDataSource = ProductDataSource(this)

        //insertDefaultProducts()

        listProductsAdapter = ListProductsAdapter(productsDataSource.getAllProducts(), this)

        binding.productsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.productsRecyclerView.adapter = listProductsAdapter

        binding.shoppingCartButton.setOnClickListener {
            val intent = Intent(this, ShoppingCartActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume(){
        super.onResume()
        listProductsAdapter.refreshData(productsDataSource.getAllProducts())
    }

    fun insertDefaultProducts() {
        val products = productsDataSource.getProductDefault()

        products.forEach { product ->
            val result = productsDataSource.insertProduct(product)

            if (result != -1L) {
                Toast.makeText(this, "Se inserto el producto \"${product.name}\" correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "ERROR - No se logró insertar el producto \"${product.name}\"", Toast.LENGTH_SHORT).show()
            }
        }
    }
}