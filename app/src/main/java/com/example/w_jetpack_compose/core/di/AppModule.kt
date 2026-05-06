package com.example.w_jetpack_compose.core.di

import com.example.w_jetpack_compose.data.repository.PostRepositoryImpl
import com.example.w_jetpack_compose.domain.repository.PostRepository
import com.example.w_jetpack_compose.domain.usecase.GetPostsUseCase
import com.example.w_jetpack_compose.ui.navigation.Navigator
import com.example.w_jetpack_compose.ui.navigation.NavigatorImpl
import com.example.w_jetpack_compose.ui.post.PostViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { NavigatorImpl() }
    single<PostRepository> { PostRepositoryImpl(get()) }
    factory { GetPostsUseCase(get()) }
    viewModel { PostViewModel(get(), get()) }
}
