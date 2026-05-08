package com.example.w_jetpack_compose.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w_jetpack_compose.data.local.entity.CartItemEntity
import com.example.w_jetpack_compose.domain.model.Post
import com.example.w_jetpack_compose.domain.repository.CartRepository
import com.example.w_jetpack_compose.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

sealed class SearchUiEvent {
    data class ShowSnackbar(val message: String) : SearchUiEvent()
}

class SearchViewModel(
    private val getPostsUseCase: GetPostsUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Post>>(emptyList())
    val searchResults: StateFlow<List<Post>> = _searchResults.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItemEntity>>(emptyList())
    val cartItems: StateFlow<List<CartItemEntity>> = _cartItems.asStateFlow()

    private val _uiEvent = MutableSharedFlow<SearchUiEvent>()
    val uiEvent: SharedFlow<SearchUiEvent> = _uiEvent.asSharedFlow()

    private var allPosts: List<Post> = emptyList()

    init {
        fetchAllPosts()
        observeCart()
    }

    private fun fetchAllPosts() {
        viewModelScope.launch {
            getPostsUseCase.executeCold().collectLatest { posts ->
                allPosts = posts
                filterPosts(_searchQuery.value)
            }
        }
    }

    private fun observeCart() {
        viewModelScope.launch {
            cartRepository.getCartItems().collectLatest { items ->
                _cartItems.value = items
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterPosts(query)
    }

    private fun filterPosts(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = allPosts.filter {
                it.title.contains(query, ignoreCase = true) || it.body.contains(query, ignoreCase = true)
            }
        }
    }

    fun addToCart(post: Post) {
        viewModelScope.launch {
            // Check if already in cart to avoid duplicates for "saved" items
            val isAlreadyInCart = _cartItems.value.any { it.itemName == post.title }
            if (isAlreadyInCart) {
                _uiEvent.emit(SearchUiEvent.ShowSnackbar("${post.title} is already saved!"))
                return@launch
            }

            val cartItem = CartItemEntity(
                itemName = post.title,
                price = 10.0,
                quantity = 1
            )
            cartRepository.addToCart(cartItem)
            _uiEvent.emit(SearchUiEvent.ShowSnackbar("Added ${post.title} to your store list!"))
        }
    }

    fun removeFromCart(itemId: Int) {
        viewModelScope.launch {
            cartRepository.deleteFromCart(itemId)
            _uiEvent.emit(SearchUiEvent.ShowSnackbar("Item removed from your list"))
        }
    }
}
