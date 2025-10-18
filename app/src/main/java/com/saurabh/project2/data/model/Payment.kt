package com.saurabh.project2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payments")
data class Payment(
    @PrimaryKey
    val paymentId: String = "",
    val customerId: String = "",
    val amount: Double = 0.0,
    val paymentMethod: String = "", // cash, online, cheque
    val paymentDate: Long = System.currentTimeMillis(),
    val reference: String = "",
    val notes: String = ""
)