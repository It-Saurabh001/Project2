package com.saurabh.project2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey
    val customerId: String = "",
    val phoneNumber: String = "",
    val name: String = "",
    val address: String = "",
    val totalDue: Double = 0.0,
    val totalPurchase: Double = 0.0,
    val lastPurchaseDate: Long = 0L,
    val createdAt: Long = System.currentTimeMillis()
)