package com.saurabh.project2.data.repository

import com.saurabh.project2.data.local.dao.CustomerDao
import com.saurabh.project2.data.model.Customer
import com.saurabh.project2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class CustomerRepositoryImpl(
    private val customerDao: CustomerDao
) : CustomerRepository {

    override suspend fun addCustomer(customer: Customer): Result<Boolean> = try {
        customerDao.insertCustomer(customer)
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateCustomer(customer: Customer): Result<Boolean> = try {
        customerDao.updateCustomer(customer)
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteCustomer(customerId: String): Result<Boolean> = try {
        val customer = customerDao.getCustomerById(customerId)
        if (customer != null) {
            customerDao.deleteCustomer(customer)
            Result.success(true)
        } else {
            Result.failure(Exception("Customer not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getCustomerById(customerId: String): Result<Customer?> = try {
        val customer = customerDao.getCustomerById(customerId)
        if (customer != null) {
            Result.success(customer)
        } else {
            Result.failure(Exception("Customer not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override fun searchCustomers(query: String): Flow<List<Customer>> {
        return customerDao.searchCustomers(query)
    }

    override fun getAllCustomers(): Flow<List<Customer>> {
        return customerDao.getAllCustomers()
    }

    override fun getCustomersWithDues(): Flow<List<Customer>> {
        return customerDao.getCustomersWithDues()
    }
}
