package com.katyrin.githubusers.data.cache

import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.room.Database
import com.katyrin.githubusers.data.room.RoomGithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GithubUsersCacheImpl @Inject constructor(
    private val db: Database
) : GithubUsersCache {
    override fun getUsers(): Single<List<GithubUser>> =
        Single.fromCallable {
            db.userDao.getAll().map { roomUser ->
                GithubUser(roomUser.id, roomUser.login, roomUser.avatarUrl, roomUser.reposUrl)
            }
        }

    override fun putUsers(users: List<GithubUser>): Completable =
        Completable.fromAction {
            val roomUsers = users.map { user ->
                RoomGithubUser(
                    user.id ?: "",
                    user.login ?: "",
                    user.avatarUrl ?: "",
                    user.reposUrl ?: ""
                )
            }
            db.userDao.insert(roomUsers)
        }.subscribeOn(Schedulers.io())
}