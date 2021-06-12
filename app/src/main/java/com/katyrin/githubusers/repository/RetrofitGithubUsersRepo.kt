package com.katyrin.githubusers.repository

import com.katyrin.githubusers.api.IDataSource
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.room.Database
import com.katyrin.githubusers.data.room.RoomGithubUser
import com.katyrin.githubusers.network.INetworkStatus
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(
    val api: IDataSource,
    val networkStatus: INetworkStatus,
    val db: Database
) : IGithubUsersRepo {
    override fun getUsers(): Single<List<GithubUser>> =
        networkStatus.isOnlineSingle()
            .flatMap { isOnline ->
                if (isOnline) {
                    api.getUsers()
                        .flatMap { users ->
                            Single.fromCallable {
                                val roomUsers = users.map { user ->
                                    RoomGithubUser(
                                        user.id ?: "",
                                        user.login ?: "",
                                        user.avatarUrl ?: "",
                                        user.reposUrl ?: ""
                                    )
                                }
                                db.userDao.insert(roomUsers)
                                users
                            }
                        }
                } else {
                    Single.fromCallable {
                        db.userDao.getAll().map { roomUser ->
                            GithubUser(
                                roomUser.id,
                                roomUser.login,
                                roomUser.avatarUrl,
                                roomUser.reposUrl
                            )
                        }
                    }
                }
            }
            .subscribeOn(Schedulers.io())
}