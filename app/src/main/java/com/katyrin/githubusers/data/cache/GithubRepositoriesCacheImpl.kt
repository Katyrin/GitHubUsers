package com.katyrin.githubusers.data.cache

import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.data.room.Database
import com.katyrin.githubusers.data.room.RoomGithubRepository
import com.katyrin.githubusers.utils.NO_SUCH_USER
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.RuntimeException
import javax.inject.Inject

class GithubRepositoriesCacheImpl @Inject constructor(
    private val db: Database
) : GithubRepositoriesCache {

    override fun getUserRepos(user: GithubUser): Single<List<GitHubRepository>> =
        Single.fromCallable {
            val roomUsers =
                user.login?.let { db.userDao.findByLogin(it) } ?: throw RuntimeException(
                    NO_SUCH_USER
                )
            return@fromCallable db.repositoryDao.findForUser(roomUsers.id).map {
                GitHubRepository(it.id, it.name, it.forksCount)
            }
        }.subscribeOn(Schedulers.io())

    override fun putUserRepos(user: GithubUser, repos: List<GitHubRepository>): Completable =
        Completable.fromAction {
            val roomUser = user.login?.let {
                db.userDao.findByLogin(it)
            } ?: throw RuntimeException(NO_SUCH_USER)
            val roomRepos = repos.map {
                RoomGithubRepository(it.id ?: "", it.name ?: "", it.forksCount ?: 0, roomUser.id)
            }
            db.repositoryDao.insert(roomRepos)
        }.subscribeOn(Schedulers.io())
}