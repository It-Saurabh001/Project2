package com.saurabh.project2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.saurabh.project2.data.local.dao.CustomerDao
import com.saurabh.project2.data.local.dao.PaymentDao
import com.saurabh.project2.data.local.dao.ProductDao
import com.saurabh.project2.data.local.dao.SaleDao
import com.saurabh.project2.data.model.Customer
import com.saurabh.project2.data.model.Payment
import com.saurabh.project2.data.model.Product
import com.saurabh.project2.data.model.Sale

@Database(
    entities = [Customer::class, Product::class, Sale::class, Payment::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun productDao(): ProductDao
    abstract fun saleDao(): SaleDao
    abstract fun paymentDao(): PaymentDao
}