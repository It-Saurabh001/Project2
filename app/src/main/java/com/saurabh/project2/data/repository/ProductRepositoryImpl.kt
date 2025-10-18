package com.saurabh.project2.data.repository

import com.saurabh.project2.data.local.dao.ProductDao
import com.saurabh.project2.data.model.Product
import com.saurabh.project2.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {

    override suspend fun addProduct(product: Product): Result<Boolean> = try {
        productDao.insertProduct(product)
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun updateProduct(product: Product): Result<Boolean> = try {
        productDao.updateProduct(product)
        Result.success(true)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun deleteProduct(productId: String): Result<Boolean> = try {
        val product = productDao.getProductById(productId)
        if (product != null) {
            productDao.deleteProduct(product)
            Result.success(true)
        } else {
            Result.failure(Exception("Product not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }


    override suspend fun getProductById(productId: String): Result<Product?> = try {
        val product = productDao.getProductById(productId)

        if (product != null){
            Result.success(product)
        }else{
            Result.failure(Exception("Product not found"))
        }
    } catch (e: Exception) {
        Result.failure(e)
        }


    override fun searchProducts(query: String): Flow<List<Product>> {
        return productDao.searchProducts(query)
    }

    override fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override fun getLowStockProducts(): Flow<List<Product>> {
        return productDao.getLowStockProducts()
    }
}
