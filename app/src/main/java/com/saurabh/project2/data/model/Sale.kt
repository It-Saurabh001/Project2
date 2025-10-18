package com.saurabh.project2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey
    val saleId: String = "",
    val customerId: String = "",
    val billNumber: String = "",
    val totalAmount: Double = 0.0,
    val paymentType: String = "", // "Cash" or "Credit"
    val paidAmount: Double = 0.0,
    val status: String = "pending", // pending, completed, cancelled
    val saleDate: Long = System.currentTimeMillis(),
    val items: List<SaleItem> = emptyList()
)