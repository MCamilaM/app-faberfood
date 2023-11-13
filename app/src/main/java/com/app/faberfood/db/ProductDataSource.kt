package com.app.faberfood.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_CATEGORY
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_DESCRIPTION
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_ID
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_NAME
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_COLUMN_PRICE
import com.app.faberfood.db.DBHelper.Companion.PRODUCT_TABLE_NAME
import com.app.faberfood.entities.Product

class ProductDataSource(context: Context){

    private val dbHelper: DBHelper = DBHelper(context)
    private val database: SQLiteDatabase = dbHelper.writableDatabase

    fun insertProduct(product: Product): Long {
        val values = ContentValues()
        values.put(PRODUCT_COLUMN_NAME, product.name)
        values.put(PRODUCT_COLUMN_DESCRIPTION, product.description)
        values.put(PRODUCT_COLUMN_PRICE, product.price)
        values.put(PRODUCT_COLUMN_CATEGORY, product.category)

        return database.insert(PRODUCT_TABLE_NAME, null, values)
    }

    fun getProductById(productId: Int): Product {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $PRODUCT_TABLE_NAME WHERE $PRODUCT_COLUMN_ID = $productId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_NAME))
        val description = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_DESCRIPTION))
        val price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_PRICE))
        val category = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_CATEGORY))

        cursor.close()
        db.close()
        return Product(id, name, description, price, category)
    }

    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $PRODUCT_TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_NAME))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_DESCRIPTION))
            val price = cursor.getDouble(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_PRICE))
            val category = cursor.getString(cursor.getColumnIndexOrThrow(PRODUCT_COLUMN_CATEGORY))

            val product = Product(id, name, description, price, category)
            productList.add(product)
        }
        cursor.close()
        db.close()
        return productList
    }


}
