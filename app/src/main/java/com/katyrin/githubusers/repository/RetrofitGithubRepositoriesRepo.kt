package com.katyrin.githubusers.repository

import com.katyrin.githubusers.api.IDataSource
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.room.Database
import com.katyrin.githubusers.data.room.RoomGithubRepository
import com.katyrin.githubusers.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: Database
) : IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser) =
        networkStatus.isOnlineSingle().flatMap { isOnline ->
            if (isOnline) {
                user.reposUrl?.let { url ->
                    api.getRepositories(url)
                        .flatMap { repositories ->
                            Single.fromCallable {
                                val roomUser = user.login?.let { db.userDao.findByLogin(it) }
                                    ?: throw RuntimeException("No such user in cache")
                                val roomRepos = repositories.map {
                                    RoomGithubRepository(
                                        it.id ?: "",
                                        it.name ?: "",
                                        it.forksCount ?: 0,
                                        roomUser.id
                                    )
                                }
                                db.repositoryDao.insert(roomRepos)
                                repositories
                            }
                        }
                } ?: Single.error<List<GitHubRepository>>(RuntimeException("User has no repos url"))
                    .subscribeOn(Schedulers.io())
            } else {
                Single.fromCallable {
                    val roomUser = user.login?.let { db.userDao.findByLogin(it) }
                        ?: throw RuntimeException("No such user in cache")
                    db.repositoryDao.findForUser(roomUser.id)
                        .map { GitHubRepository(it.id, it.name, it.forksCount) }
                }
            }
        }.subscribeOn(Schedulers.io())
}