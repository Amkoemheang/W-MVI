package com.example.w_jetpack_compose.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w_jetpack_compose.domain.model.Post
import com.example.w_jetpack_compose.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PostViewModel(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    // Hot Flow (StateFlow) for UI State
    private val _postsState = MutableStateFlow<PostUiState>(PostUiState.Loading)
    val postsState: StateFlow<PostUiState> = _postsState.asStateFlow()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            _postsState.value = PostUiState.Loading
            
            // Example of using Cold Flow from UseCase
            getPostsUseCase.executeCold()
                .catch { e ->
                    _postsState.value = PostUiState.Error(e.message ?: "Unknown Error")
                }
                .collect { posts ->
                    _postsState.value = PostUiState.Success(posts)
                }
        }
    }
}

sealed class PostUiState {
    data object Loading : PostUiState()
    data class Success(val posts: List<Post>) : PostUiState()
    data class Error(val message: String) : PostUiState()
}
