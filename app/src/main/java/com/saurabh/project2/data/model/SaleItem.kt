package com.saurabh.project2.data.model

data class SaleItem(
    val productId: String = "",
    val productName: String = "",
    val quantity: Double = 0.0,
    val rate: Double = 0.0,
    val total: Double = 0.0
)
