package com.saurabh.project2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val productId: String = "",
    val name: String = "",
    val quantity: Double = 0.0,
    val unit: String = "", // bags, pieces, liters, etc.
    val buyPrice: Double = 0.0,
    val sellPrice: Double = 0.0,
    val lowStockLevel: Double = 10.0,
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)