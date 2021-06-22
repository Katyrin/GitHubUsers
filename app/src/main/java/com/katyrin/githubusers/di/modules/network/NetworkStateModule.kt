package com.katyrin.githubusers.di.modules.network

import com.katyrin.githubusers.network.AndroidNetworkStatus
import com.katyrin.githubusers.network.INetworkStatus
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface NetworkStateModule {

    @Singleton
    @Binds
    fun networkStatus(androidNetworkStatus: AndroidNetworkStatus): INetworkStatus
}