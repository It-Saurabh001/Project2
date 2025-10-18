package com.saurabh.project2.di

import android.content.Context
import com.saurabh.project2.data.local.LocalDataSource
import com.saurabh.project2.data.local.UserPreferences
import com.saurabh.project2.data.remote.CloudinaryService
import com.saurabh.project2.data.repository.AuthRepositoryImpl
import com.saurabh.project2.domain.repository.AuthRepository
import com.saurabh.project2.domain.usecase.ResetPasswordUseCase
import com.saurabh.project2.domain.usecase.SendOtpUseCase
import com.saurabh.project2.domain.usecase.UpdateUserProfileUseCase
import com.saurabh.project2.domain.usecase.VerifyOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.saurabh.project2.data.local.dao.CustomerDao
import com.saurabh.project2.data.local.dao.ProductDao
import com.saurabh.project2.data.remote.FirebaseAuthDataSource
import com.saurabh.project2.data.repository.CustomerRepositoryImpl
import com.saurabh.project2.data.repository.ProductRepositoryImpl
import com.saurabh.project2.domain.repository.CustomerRepository
import com.saurabh.project2.domain.repository.ProductRepository
import com.saurabh.project2.domain.usecase.AddCustomerUseCase
import com.saurabh.project2.domain.usecase.GetAllCustomersUseCase
import com.saurabh.project2.domain.usecase.GetCustomerByIdUseCase

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()


    // ==========================================
    // Preferences & Local Storage
    // ==========================================

    @Singleton
    @Provides
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(userPreferences: UserPreferences): LocalDataSource {
        return LocalDataSource(userPreferences)
    }

    // ==========================================
    // Remote Data Sources
    // ==========================================

    @Singleton
    @Provides
    fun provideFirebaseAuthDataSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseAuthDataSource {
        return FirebaseAuthDataSource(firebaseAuth, firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideCloudinaryService(@ApplicationContext context: Context): CloudinaryService {
        return CloudinaryService(context)
    }

    // ==========================================
    // Repository Layer
    // ==========================================

    @Singleton
    @Provides
    fun provideAuthRepository(
        firebaseAuthDataSource: FirebaseAuthDataSource,
        localDataSource: LocalDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuthDataSource, localDataSource)
    }

    @Singleton
    @Provides
    fun provideImageRepository(cloudinaryService: CloudinaryService): ImageRepository {
        return ImageRepositoryImpl(cloudinaryService)
    }

    @Singleton
    @Provides
    fun provideCustomerRepository(
        customerDao: CustomerDao
    ): CustomerRepository {
        return CustomerRepositoryImpl(customerDao)
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        productDao: ProductDao
    ): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }

    // ==========================================
    // Use Cases - Authentication
    // ==========================================

    @Singleton
    @Provides
    fun provideSendOtpUseCase(authRepository: AuthRepository): SendOtpUseCase {
        return SendOtpUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideVerifyOtpUseCase(authRepository: AuthRepository): VerifyOtpUseCase {
        return VerifyOtpUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideResetPasswordUseCase(authRepository: AuthRepository): ResetPasswordUseCase {
        return ResetPasswordUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateUserProfileUseCase(authRepository: AuthRepository): UpdateUserProfileUseCase {
        return UpdateUserProfileUseCase(authRepository)
    }

    // ==========================================
    // Use Cases - Customer
    // ==========================================

    @Singleton
    @Provides
    fun provideAddCustomerUseCase(customerRepository: CustomerRepository): AddCustomerUseCase {
        return AddCustomerUseCase(customerRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllCustomersUseCase(customerRepository: CustomerRepository): GetAllCustomersUseCase {
        return GetAllCustomersUseCase(customerRepository)
    }

    @Singleton
    @Provides
    fun provideGetCustomerByIdUseCase(customerRepository: CustomerRepository): GetCustomerByIdUseCase {
        return GetCustomerByIdUseCase(customerRepository)
    }

}
