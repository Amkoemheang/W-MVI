package com.example.w_jetpack_compose.data.remote

import com.example.w_jetpack_compose.domain.model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
