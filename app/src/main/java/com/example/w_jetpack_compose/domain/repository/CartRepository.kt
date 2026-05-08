package com.example.w_jetpack_compose.domain.repository

import com.example.w_jetpack_compose.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun getCartItems(): Flow<List<CartItemEntity>>
    suspend fun addToCart(item: CartItemEntity)
    suspend fun deleteFromCart(id: Int)
    suspend fun clearCart()
}
