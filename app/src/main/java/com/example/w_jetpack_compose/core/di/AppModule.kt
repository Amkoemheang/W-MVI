package com.example.w_jetpack_compose.core.di

import androidx.room.Room
import com.example.w_jetpack_compose.core.connectivity.ConnectivityObserver
import com.example.w_jetpack_compose.core.connectivity.NetworkConnectivityObserver
import com.example.w_jetpack_compose.data.local.AppDatabase
import com.example.w_jetpack_compose.data.repository.CartRepositoryImpl
import com.example.w_jetpack_compose.data.repository.PostRepositoryImpl
import com.example.w_jetpack_compose.domain.repository.CartRepository
import com.example.w_jetpack_compose.domain.repository.PostRepository
import com.example.w_jetpack_compose.domain.usecase.GetPostsUseCase
import com.example.w_jetpack_compose.ui.navigation.Navigator
import com.example.w_jetpack_compose.ui.navigation.NavigatorImpl
import com.example.w_jetpack_compose.ui.post.PostViewModel
import com.example.w_jetpack_compose.ui.profile.ProfileViewModel
import com.example.w_jetpack_compose.ui.search.SearchViewModel
import com.example.w_jetpack_compose.ui.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Navigation & Connectivity
    single<Navigator> { NavigatorImpl() }
    single<ConnectivityObserver> { NetworkConnectivityObserver(androidContext()) }

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "w_database"
        ).build()
    }
    single { get<AppDatabase>().cartDao() }

    // Repositories
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get()) }

    // UseCases
    factory { GetPostsUseCase(get()) }

    // ViewModels
    viewModel { PostViewModel(get(), get()) }
    viewModel { SearchViewModel(get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}
