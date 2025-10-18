package com.saurabh.project2.domain.repository

import com.saurabh.project2.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun addProduct(product: Product): Result<Boolean>
    suspend fun updateProduct(product: Product): Result<Boolean>
    suspend fun deleteProduct(productId: String): Result<Boolean>
    suspend fun getProductById(productId: String): Result<Product?>
    fun searchProducts(query: String): Flow<List<Product>>
    fun getAllProducts(): Flow<List<Product>>
    fun getLowStockProducts(): Flow<List<Product>>
}