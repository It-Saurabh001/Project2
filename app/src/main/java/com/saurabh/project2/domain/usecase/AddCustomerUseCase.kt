package com.saurabh.project2.domain.usecase

import com.saurabh.project2.data.model.Customer
import com.saurabh.project2.domain.repository.CustomerRepository
import java.util.UUID

class AddCustomerUseCase(private val customerRepository: CustomerRepository) {
    suspend operator fun invoke(name: String, phone: String, address: String): Result<Boolean> {
        return if (name.isEmpty() || phone.isEmpty()) {
            Result.Failure(Exception("Name and phone are required"))
        } else {
            val customer = Customer(
                customerId = UUID.randomUUID().toString(),
                name = name,
                phoneNumber = phone,
                address = address
            )
            customerRepository.addCustomer(customer)
        }
    }
}