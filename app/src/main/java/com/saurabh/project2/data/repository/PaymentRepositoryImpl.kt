package com.saurabh.project2.data.repository

import com.saurabh.project2.data.local.dao.PaymentDao
import com.saurabh.project2.data.model.Payment
import com.saurabh.project2.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow

class PaymentRepositoryImpl(
    private val paymentDao: PaymentDao
) : PaymentRepository {

    override suspend fun addPayment(payment: Payment): Result<Boolean> = try {
        paymentDao.insertPayment(payment)
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updatePayment(payment: Payment): Result<Boolean> = try {
        paymentDao.updatePayment(payment)
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deletePayment(paymentId: String): Result<Boolean> = try {
        val payment = paymentDao.getPaymentById(paymentId)
        if (payment != null) {
            paymentDao.deletePayment(payment)
            Result.success(true)
        } else {
            Result.failure(Exception("Payment not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getPaymentById(paymentId: String): Result<Payment?> = try {
        val payment = paymentDao.getPaymentById(paymentId)
        if (payment != null) {
            Result.success(payment)
        } else {
            Result.failure(Exception("Payment not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun getPaymentsByCustomerId(customerId: String): Flow<List<Payment>> {
        return paymentDao.getPaymentsByCustomerId(customerId)
    }

    override fun getTodayPaymentsTotal(): Flow<Double?> {
        return paymentDao.getTodayPaymentsTotal()
    }

    override fun getRecentPayments(): Flow<List<Payment>> {
        return paymentDao.getRecentPayments()
    }
}
