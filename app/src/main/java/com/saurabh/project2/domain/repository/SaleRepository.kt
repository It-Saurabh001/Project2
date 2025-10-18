package com.saurabh.project2.domain.repository

import com.saurabh.project2.data.model.Sale
import kotlinx.coroutines.flow.Flow

interface SaleRepository {
    suspend fun addSale(sale: Sale): Result<Boolean>
    suspend fun updateSale(sale: Sale): Result<Boolean>
    suspend fun deleteSale(saleId: String): Result<Boolean>
    suspend fun getSaleById(saleId: String): Result<Sale?>
    fun getSalesByCustomerId(customerId: String): Flow<List<Sale>>
    fun getRecentSales(): Flow<List<Sale>>
    fun getTodaySalesTotal(): Flow<Double?>
    fun getPendingSales(): Flow<List<Sale>>
}