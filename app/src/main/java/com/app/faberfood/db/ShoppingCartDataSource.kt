package com.app.faberfood.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.app.faberfood.db.DBHelper.Companion.SC_COLUMN_ID
import com.app.faberfood.db.DBHelper.Companion.SC_COLUMN_PRODUCT_ID_FK
import com.app.faberfood.db.DBHelper.Companion.SC_COLUMN_QUANTITY
import com.app.faberfood.db.DBHelper.Companion.SC_TABLE_NAME
import com.app.faberfood.entities.ItemShoppingCart
import com.app.faberfood.entities.Product
import com.app.faberfood.entities.User

class ShoppingCartDataSource(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)
    private val productDataSource: ProductDataSource = ProductDataSource(context)

    fun addProductToShoppingCart(product: Product): Long {
        val database = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(SC_COLUMN_PRODUCT_ID_FK, product.id)
        values.put(SC_COLUMN_QUANTITY, 1)

        val result = database.insert(DBHelper.SC_TABLE_NAME, null, values)
        database.close()
        return  result
    }

    @SuppressLint("Range")
    fun getAllProducts(): List<ItemShoppingCart> {
        val itemList = mutableListOf<ItemShoppingCart>()
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $SC_TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val idShoppingCart = cursor.getInt(cursor.getColumnIndex(SC_COLUMN_ID))
            val productId = cursor.getInt(cursor.getColumnIndex(SC_COLUMN_PRODUCT_ID_FK))
            val quantity = cursor.getInt(cursor.getColumnIndex(SC_COLUMN_QUANTITY))

            val product = productDataSource.getProductById(productId)

            val item = ItemShoppingCart(idShoppingCart, product, quantity)
            itemList.add(item)
        }
        cursor.close()
        db.close()
        return itemList

    }

    fun updateProductQuantity(id: Int, nuevaCantidad: Int): Int {
        val database = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(SC_COLUMN_QUANTITY, nuevaCantidad)

        val result = database.update(
            SC_TABLE_NAME,
            values,
            "${SC_COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        database.close()
        return  result
    }

    fun deleteProduct(id: Int): Int {
        val database = dbHelper.writableDatabase
        val result = database.delete(
            SC_TABLE_NAME,
            "${SC_COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        database.close()
        return  result
    }

    fun getSubTotal(products: List<ItemShoppingCart>): Double{
        val database = dbHelper.readableDatabase
        var subTotal = 0.0

        if(products.isNotEmpty()){
            products.forEach{ item ->
                subTotal += item.product.price * item.quantity
            }
        }
        database.close()
        return subTotal
    }


}