package com.example.w_jetpack_compose.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w_jetpack_compose.data.local.entity.CartItemEntity
import com.example.w_jetpack_compose.domain.repository.CartRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    // Dummy products for POS
    val products = listOf(
        Product("Burger", 5.99),
        Product("Pizza", 8.99),
        Product("Coke", 1.50),
        Product("Fries", 2.50)
    )

    fun addItemToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(
                CartItemEntity(
                    itemName = product.name,
                    price = product.price,
                    quantity = 1
                )
            )
        }
    }
}

data class Product(val name: String, val price: Double)
