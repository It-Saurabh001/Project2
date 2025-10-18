package com.saurabh.project2.domain.usecase

import com.saurabh.project2.data.model.Customer
import com.saurabh.project2.domain.repository.CustomerRepository

class GetCustomerByIdUseCase(private val customerRepository: CustomerRepository) {
    suspend operator fun invoke(customerId: String): Result<Customer?> {
        return if (customerId.isEmpty()) {
            Result.Failure(Exception("Customer ID is empty"))
        } else {
            customerRepository.getCustomerById(customerId)
        }
    }
}