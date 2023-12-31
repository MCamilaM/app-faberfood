package com.app.faberfood.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "faberfood.db"
        const val DATABASE_VERSION = 1

        //Table User
        const val USER_TABLE_NAME = "user"
        const val USER_COLUMN_ID = "id"

        //Table Product
        const val PRODUCT_TABLE_NAME = "product"
        const val PRODUCT_COLUMN_ID = "id"
        const val PRODUCT_COLUMN_IMAGE = "image"
        const val PRODUCT_COLUMN_NAME = "name"
        const val PRODUCT_COLUMN_DESCRIPTION = "description"
        const val PRODUCT_COLUMN_PRICE = "price"
        const val PRODUCT_COLUMN_CATEGORY = "category"

        //Table Shopping Cart (SC)
        const val SC_TABLE_NAME = "shopping_cart"
        const val SC_COLUMN_ID = "id"
        const val SC_COLUMN_PRODUCT_ID_FK = "product_id"
        const val SC_COLUMN_QUANTITY = "quantity"

        //TODO(SQL statement to create the User table)
        private const val SQL_CREATE_USER_TABLE =
            "CREATE TABLE $USER_TABLE_NAME ($USER_COLUMN_ID INTEGER PRIMARY KEY)"

        const val SQL_CREATE_PRODUCT_TABLE = """
            CREATE TABLE IF NOT EXISTS $PRODUCT_TABLE_NAME (
            $PRODUCT_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $PRODUCT_COLUMN_IMAGE TEXT,
            $PRODUCT_COLUMN_NAME TEXT,
            $PRODUCT_COLUMN_DESCRIPTION TEXT,
            $PRODUCT_COLUMN_PRICE REAL,
            $PRODUCT_COLUMN_CATEGORY TEXT
            )
        """


        // SQL statement to create the Shopping Cart table
        private const val SQL_CREATE_SC_TABLE =
            "CREATE TABLE $SC_TABLE_NAME ($SC_COLUMN_ID INTEGER PRIMARY KEY, " +
                    "$SC_COLUMN_PRODUCT_ID_FK INTEGER, " +
                    "$SC_COLUMN_QUANTITY INTEGER, " +
                    "FOREIGN KEY($SC_COLUMN_PRODUCT_ID_FK) REFERENCES $PRODUCT_TABLE_NAME($PRODUCT_COLUMN_ID))"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USER_TABLE)
        db?.execSQL(SQL_CREATE_PRODUCT_TABLE)
        db?.execSQL(SQL_CREATE_SC_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $PRODUCT_TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS $SC_TABLE_NAME")
        onCreate(db)
    }

}