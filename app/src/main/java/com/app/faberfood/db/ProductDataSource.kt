package com.app.faberfood.db

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.app.faberfood.R
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_CATEGORY
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_DESCRIPTION
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_ID
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_IMAGE
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_NAME
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_PRICE
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_TABLE_NAME
import com.app.faberfood.entities.Product

class ProductDataSource(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)

    fun insertProduct(product: Product): Long {
        val database = dbHelper.writableDatabase
        if (product != null &&
            !product.name.isNullOrEmpty() &&
            !product.description.isNullOrEmpty() &&
            product.price != null &&
            !product.category.isNullOrEmpty()
        ) {

            val values = ContentValues()
            values.put(PRODUCT_COLUMN_IMAGE, product.image)
            values.put(PRODUCT_COLUMN_NAME, product.name)
            values.put(PRODUCT_COLUMN_DESCRIPTION, product.description)
            values.put(PRODUCT_COLUMN_PRICE, product.price)
            values.put(PRODUCT_COLUMN_CATEGORY, product.category)

            val result = database.insert(PRODUCT_TABLE_NAME, null, values)
            database.close()
            return result
        }
        return -1
    }

    fun getProductById(productId: Int): Product {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $PRODUCT_TABLE_NAME WHERE $PRODUCT_COLUMN_ID = $productId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_ID))
        val image = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_IMAGE))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_NAME))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_DESCRIPTION))
        val price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_PRICE))
        val category = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_CATEGORY))

        cursor.close()
        db.close()
        return Product(id, image, name, description, price, category)
    }

    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $PRODUCT_TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_ID))
            val image = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_IMAGE))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_NAME))
            val description =
                cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_DESCRIPTION))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_PRICE))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_CATEGORY))

            val product = Product(id, image, name, description, price, category)
            productList.add(product)
        }
        cursor.close()
        db.close()
        Log.e("database", "${productList.size}")
        return productList
    }

    fun getProductDefault(): ArrayList<Product>{
        val product1 = Product(
            id = 1,
            image = R.drawable.product_burger,
            name = "Big Burger",
            description = "Hamburguesa con carne a la parrilla, queso fundido, lechuga fresca y tomate.",
            price = 5.99,
            category = "Comida Rápida")
        val product2 = Product(
            id = 2,
            image = R.drawable.product_hotdog,
            name = "Hotdog con Chili",
            description = "Hotdog clásico con una jugosa salchicha, pan recién horneado y toppings de mostaza, kétchup y cebolla fresca.",
            price = 3.99,
            category = "Comida Rápida"
        )
        val product3 = Product(
            id = 3,
            image = R.drawable.product_taco,
            name = "Taco al Pastor",
            description = "Taco con tortilla de maíz caliente rellena de carne sazonada, cebolla, cilantro y tu elección de salsa.",
            price = 2.50,
            category = "Comida Rápida"
        )

       return arrayListOf(product1, product2, product3)
    }


}
