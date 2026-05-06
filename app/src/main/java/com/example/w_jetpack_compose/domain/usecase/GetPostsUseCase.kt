package com.example.w_jetpack_compose.domain.usecase

import com.example.w_jetpack_compose.domain.model.Post
import com.example.w_jetpack_compose.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val repository: PostRepository) {
    fun executeCold(): Flow<List<Post>> = repository.getPostsCold()
    
    suspend fun executeHot(): List<Post> = repository.getPosts()
}
