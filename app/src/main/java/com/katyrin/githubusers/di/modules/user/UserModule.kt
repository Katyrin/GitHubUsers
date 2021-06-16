package com.katyrin.githubusers.di.modules.user

import android.content.Context
import androidx.room.Room
import com.katyrin.githubusers.data.cache.GithubRepositoriesCache
import com.katyrin.githubusers.data.cache.GithubRepositoriesCacheImpl
import com.katyrin.githubusers.data.cache.GithubUsersCache
import com.katyrin.githubusers.data.cache.GithubUsersCacheImpl
import com.katyrin.githubusers.data.room.Database
import com.katyrin.githubusers.di.modules.network.NetworkModule
import com.katyrin.githubusers.repository.IGithubRepositoriesRepo
import com.katyrin.githubusers.repository.IGithubUsersRepo
import com.katyrin.githubusers.repository.RetrofitGithubRepositoriesRepo
import com.katyrin.githubusers.repository.RetrofitGithubUsersRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
interface UserModule {

    @Singleton
    @Binds
    fun usersRepo(retrofitGithubUsersRepo: RetrofitGithubUsersRepo): IGithubUsersRepo

    @Singleton
    @Binds
    fun usersCache(githubUsersCacheImpl: GithubUsersCacheImpl): GithubUsersCache

    @Singleton
    @Binds
    fun githubRepositoriesCache(
        githubRepositoriesCacheImpl: GithubRepositoriesCacheImpl
    ): GithubRepositoriesCache

    @Singleton
    @Binds
    fun iGithubRepositoriesRepo(
        retrofitGithubRepositoriesRepo: RetrofitGithubRepositoriesRepo
    ): IGithubRepositoriesRepo

    companion object {
        @Singleton
        @Provides
        fun database(context: Context): Database =
            Room.databaseBuilder(context, Database::class.java, Database.DB_NAME).build()
    }
}