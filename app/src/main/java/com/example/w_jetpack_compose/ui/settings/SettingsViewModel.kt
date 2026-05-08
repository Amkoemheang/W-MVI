package com.example.w_jetpack_compose.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w_jetpack_compose.data.local.entity.CartItemEntity
import com.example.w_jetpack_compose.domain.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItemEntity>> = cartRepository.getCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun removeItem(id: Int) {
        viewModelScope.launch {
            cartRepository.deleteFromCart(id)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            cartRepository.clearCart()
        }
    }
}
