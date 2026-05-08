package com.example.w_jetpack_compose.data.repository

import com.example.w_jetpack_compose.data.remote.ApiService
import com.example.w_jetpack_compose.domain.model.Post
import com.example.w_jetpack_compose.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostRepositoryImpl(
    private val apiService: ApiService
) : PostRepository {

    override fun getPostsCold(): Flow<List<Post>> = flow {
        val posts = apiService.getPosts()
        emit(posts)
    }

    override suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}
