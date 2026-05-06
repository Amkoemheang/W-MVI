package com.example.w_jetpack_compose.domain.repository

import com.example.w_jetpack_compose.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPostsCold(): Flow<List<Post>>
    suspend fun getPosts(): List<Post>
}
