package com.katyrin.githubusers.repository

import com.katyrin.githubusers.api.IDataSource
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.cache.GithubRepositoriesCache
import com.katyrin.githubusers.network.INetworkStatus
import com.katyrin.githubusers.utils.NO_SUCH_USER
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    private val networkStatus: INetworkStatus,
    private val githubRepositoriesCache: GithubRepositoriesCache
) : IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser): Single<List<GitHubRepository>> =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepositories(url)
                        .flatMap { repositories ->
                            githubRepositoriesCache.putUserRepos(user, repositories)
                                .toSingleDefault(repositories)
                        }
                } ?: Single.error<List<GitHubRepository>>(RuntimeException(NO_SUCH_USER))
                    .subscribeOn(Schedulers.io())
            } else {
                githubRepositoriesCache.getUserRepos(user)
            }
        }.subscribeOn(Schedulers.io())
}