package com.saurabh.project2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saurabh.project2.data.model.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: Payment)

    @Update
    suspend fun updatePayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * FROM payments WHERE paymentId = :paymentId")
    suspend fun getPaymentById(paymentId: String): Payment?

    @Query("SELECT * FROM payments WHERE customerId = :customerId ORDER BY paymentDate DESC")
    fun getPaymentsByCustomerId(customerId: String): Flow<List<Payment>>

    @Query("SELECT SUM(amount) FROM payments WHERE DATE(paymentDate/1000, 'unixepoch') = DATE('now')")
    fun getTodayPaymentsTotal(): Flow<Double?>

    @Query("SELECT * FROM payments ORDER BY paymentDate DESC LIMIT 50")
    fun getRecentPayments(): Flow<List<Payment>>
}