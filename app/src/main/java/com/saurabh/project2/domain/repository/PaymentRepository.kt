package com.saurabh.project2.domain.repository

import com.saurabh.project2.data.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun addPayment(payment: Payment): Result<Boolean>
    suspend fun updatePayment(payment: Payment): Result<Boolean>
    suspend fun deletePayment(paymentId: String): Result<Boolean>
    suspend fun getPaymentById(paymentId: String): Result<Payment?>
    fun getPaymentsByCustomerId(customerId: String): Flow<List<Payment>>
    fun getTodayPaymentsTotal(): Flow<Double?>
    fun getRecentPayments(): Flow<List<Payment>>
}