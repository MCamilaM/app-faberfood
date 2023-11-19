package com.app.faberfood.entities

data class Product(
    val id: Int,
    val image: Int,
    val name: String,
    val description: String,
    val price: Double,
    val category: String
)