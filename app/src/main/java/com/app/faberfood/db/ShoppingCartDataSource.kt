package com.app.faberfood.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.app.faberfood.db.DBHelper.Companion.SC_COLUMN_ID
import com.app.faberfood.db.DBHelper.Companion.SC_COLUMN_PRODUCT_ID_FK
import com.app.faberfood.db.DBHelper.Companion.SC_COLUMN_QUANTITY
import com.app.faberfood.entities.ItemShoppingCart
import com.app.faberfood.entities.Product
import com.app.faberfood.entities.User

class ShoppingCartDataSource(context: Context) {

    private val dbHelper: DBHelper = DBHelper(context)
    private val database: SQLiteDatabase = dbHelper.writableDatabase
    private val productDataSource: ProductDataSource = ProductDataSource(context)

    fun addProductToShoppingCart(user: User, product: Product): Long {
        val values = ContentValues()
        values.put(SC_COLUMN_PRODUCT_ID_FK, product.id)
        values.put(SC_COLUMN_QUANTITY, 1)

        return database.insert(DBHelper.SC_TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun getAllProductsOfShoppingCart(): List<ItemShoppingCart> {
        val itemList = mutableListOf<ItemShoppingCart>()
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $DBHelper.SC_TABLE_NAME"
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
//
//    fun actualizarCantidadProductoEnCarrito(id: Long, nuevaCantidad: Int): Int {
//        val values = ContentValues()
//        values.put(DBHelper.COLUMN_CANTIDAD, nuevaCantidad)
//
//        return database.update(
//            DBHelper.TABLE_NAME_CARRITO,
//            values,
//            "${DBHelper.COLUMN_ID} = ?",
//            arrayOf(id.toString())
//        )
//    }
//
//    fun eliminarProductoDelCarrito(id: Long): Int {
//        return database.delete(
//            DBHelper.TABLE_NAME_CARRITO,
//            "${DBHelper.COLUMN_ID} = ?",
//            arrayOf(id.toString())
//        )
//    }



}