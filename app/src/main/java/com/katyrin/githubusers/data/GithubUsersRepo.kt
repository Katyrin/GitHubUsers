package com.katyrin.githubusers.data

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class GithubUsersRepo {
    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers(): Single<List<GithubUser>> =
        Single.create<List<GithubUser>> { emitter ->
            emitter.onSuccess(repositories)
        }.subscribeOn(Schedulers.io())
}