package com.katyrin.githubusers.repository

import com.katyrin.githubusers.api.IDataSource
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.cache.GithubUsersCache
import com.katyrin.githubusers.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RetrofitGithubUsersRepo @Inject constructor(
    private val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val githubUsersCache: GithubUsersCache
) : IGithubUsersRepo {
    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle()
            .flatMap { isOnline ->
                if (isOnline) {
                    api.getUsers()
                        .flatMap { users ->
                            githubUsersCache.putUsers(users).toSingleDefault(users)
                        }
                } else {
                    githubUsersCache.getUsers()
                }
            }.subscribeOn(Schedulers.io())
}