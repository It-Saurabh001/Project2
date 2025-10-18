package com.saurabh.project2.data.repository

import com.saurabh.project2.data.remote.CloudinaryService
import com.saurabh.project2.domain.repository.ImageRepository

class ImageRepositoryImpl(
    private val cloudinaryService: CloudinaryService
) : ImageRepository {

    override suspend fun uploadImage(imagePath: String, folder: String): Result<String> {
        return cloudinaryService.uploadImage(imagePath, folder)
    }

    override suspend fun uploadMultipleImages(imagePaths: List<String>, folder: String): Result<List<String>> {
        return cloudinaryService.uploadMultipleImages(imagePaths, folder)
    }
}
