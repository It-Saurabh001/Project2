package com.saurabh.project2.di

import android.content.Context
import androidx.room.Room
import com.saurabh.project2.data.local.AppDatabase
import com.saurabh.project2.data.local.dao.CustomerDao
import com.saurabh.project2.data.local.dao.PaymentDao
import com.saurabh.project2.data.local.dao.ProductDao
import com.saurabh.project2.data.local.dao.SaleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "buildmate_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCustomerDao(database: AppDatabase): CustomerDao = database.customerDao()

    @Singleton
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun provideSaleDao(database: AppDatabase): SaleDao = database.saleDao()

    @Singleton
    @Provides
    fun providePaymentDao(database: AppDatabase): PaymentDao = database.paymentDao()
}