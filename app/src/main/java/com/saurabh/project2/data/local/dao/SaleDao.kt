package com.saurabh.project2.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saurabh.project2.data.model.Sale
import kotlinx.coroutines.flow.Flow


@Dao
interface SaleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(sale: Sale)

    @Update
    suspend fun updateSale(sale: Sale)

    @Delete
    suspend fun deleteSale(sale: Sale)

    @Query("SELECT * FROM sales WHERE saleId = :saleId")
    suspend fun getSaleById(saleId: String): Sale?

    @Query("SELECT * FROM sales WHERE customerId = :customerId ORDER BY saleDate DESC")
    fun getSalesByCustomerId(customerId: String): Flow<List<Sale>>

    @Query("SELECT * FROM sales ORDER BY saleDate DESC LIMIT 50")
    fun getRecentSales(): Flow<List<Sale>>

    @Query("SELECT SUM(totalAmount) FROM sales WHERE DATE(saleDate/1000, 'unixepoch') = DATE('now')")
    fun getTodaySalesTotal(): Flow<Double?>

    @Query("SELECT * FROM sales WHERE paymentType = 'Credit' AND status = 'pending' ORDER BY saleDate DESC")
    fun getPendingSales(): Flow<List<Sale>>
}