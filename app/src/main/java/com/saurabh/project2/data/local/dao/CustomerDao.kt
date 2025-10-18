package com.saurabh.project2.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saurabh.project2.data.model.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer)

    @Update
    suspend fun updateCustomer(customer: Customer)

    @Delete
    suspend fun deleteCustomer(customer: Customer)

    @Query("SELECT * FROM customers WHERE customerId = :customerId")
    suspend fun getCustomerById(customerId: String): Customer?

    @Query("SELECT * FROM customers WHERE name LIKE '%' || :search || '%' OR phoneNumber LIKE '%' || :search || '%'")
    fun searchCustomers(search: String): Flow<List<Customer>>

    @Query("SELECT * FROM customers ORDER BY lastPurchaseDate DESC")
    fun getAllCustomers(): Flow<List<Customer>>

    @Query("SELECT * FROM customers WHERE totalDue > 0 ORDER BY totalDue DESC")
    fun getCustomersWithDues(): Flow<List<Customer>>
}