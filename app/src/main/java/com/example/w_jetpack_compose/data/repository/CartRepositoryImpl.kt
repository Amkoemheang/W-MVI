package com.example.w_jetpack_compose.data.repository

import com.example.w_jetpack_compose.data.local.dao.CartDao
import com.example.w_jetpack_compose.data.local.entity.CartItemEntity
import com.example.w_jetpack_compose.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class CartRepositoryImpl(
    private val cartDao: CartDao
) : CartRepository {
    override fun getCartItems(): Flow<List<CartItemEntity>> = cartDao.getAllCartItems()

    override suspend fun addToCart(item: CartItemEntity) {
        cartDao.insertCartItem(item)
    }

    override suspend fun deleteFromCart(id: Int) {
        cartDao.deleteCartItem(id)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}
