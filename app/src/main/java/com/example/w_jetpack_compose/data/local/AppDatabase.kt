package com.example.w_jetpack_compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.w_jetpack_compose.data.local.dao.CartDao
import com.example.w_jetpack_compose.data.local.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}
