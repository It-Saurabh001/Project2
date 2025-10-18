package com.saurabh.project2.data.model

data class User(
    val userId: String = "",
    val phoneNumber: String = "",
    val shopName: String = "",
    val ownerName: String = "",
    val address: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val profileImageUrl: String = "",
    val theme: String = "light"
)
