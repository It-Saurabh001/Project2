package com.saurabh.project2.domain.repository

import com.saurabh.project2.data.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    suspend fun addCustomer(customer: Customer): Result<Boolean>
    suspend fun updateCustomer(customer: Customer): Result<Boolean>
    suspend fun deleteCustomer(customerId: String): Result<Boolean>
    suspend fun getCustomerById(customerId: String): Result<Customer?>
    fun searchCustomers(query: String): Flow<List<Customer>>
    fun getAllCustomers(): Flow<List<Customer>>
    fun getCustomersWithDues(): Flow<List<Customer>>
}