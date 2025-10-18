package com.saurabh.project2.domain.repository

interface ImageRepository {
    suspend fun uploadImage(imagePath: String, folder: String): Result<String>
    suspend fun uploadMultipleImages(imagePaths: List<String>, folder: String): Result<List<String>>
}
