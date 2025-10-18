package com.saurabh.project2.domain.usecase

import com.saurabh.project2.data.model.Customer
import com.saurabh.project2.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    operator fun invoke(): Flow<List<Customer>> {
        return customerRepository.getAllCustomers()
    }
}