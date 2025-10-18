package com.saurabh.project2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saurabh.project2.data.model.Customer
import com.saurabh.project2.data.model.OtpState
import com.saurabh.project2.data.model.User
import com.saurabh.project2.domain.usecase.AddCustomerUseCase
import com.saurabh.project2.domain.usecase.GetAllCustomersUseCase
import com.saurabh.project2.domain.usecase.GetCustomerByIdUseCase
import com.saurabh.project2.domain.usecase.ResetPasswordUseCase
import com.saurabh.project2.domain.usecase.SendOtpUseCase
import com.saurabh.project2.domain.usecase.UpdateUserProfileUseCase
import com.saurabh.project2.domain.usecase.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.fold

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val addCustomerUseCase: AddCustomerUseCase,
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
    private val getCustomerByIdUseCase: GetCustomerByIdUseCase
) : ViewModel() {

    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
    val customers: StateFlow<List<Customer>> = _customers.asStateFlow()

    private val _selectedCustomer = MutableStateFlow<Customer?>(null)
    val selectedCustomer: StateFlow<Customer?> = _selectedCustomer.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadAllCustomers()
    }

    fun loadAllCustomers() {
        viewModelScope.launch {
            getAllCustomersUseCase().collect { customers ->
                _customers.value = customers
            }
        }
    }

    fun addCustomer(name: String, phone: String, address: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = addCustomerUseCase(name, phone, address)

            result.fold(
                onSuccess = {
                    _loading.value = false
                    loadAllCustomers()
                },
                onFailure = { exception ->
                    _error.value = exception.message
                    _loading.value = false
                }
            )
        }
    }

    fun getCustomerById(customerId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = getCustomerByIdUseCase(customerId)

            result.fold(
                onSuccess = { customer ->
                    _selectedCustomer.value = customer
                    _loading.value = false
                },
                onFailure = { exception ->
                    _error.value = exception.message
                    _loading.value = false
                }
            )
        }
    }

    fun clearError() {
        _error.value = null
    }
}