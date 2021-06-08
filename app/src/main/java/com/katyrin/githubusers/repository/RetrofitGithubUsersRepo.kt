package com.katyrin.githubusers.repository

import com.katyrin.githubusers.api.IDataSource
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.utils.ERROR_NOT_FOUND_USERS
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(val api: IDataSource): IGithubUsersRepo {
    override fun getUsers(): Single<List<GithubUser>> =
        api.getUsers().subscribeOn(Schedulers.io()) ?:
            Single.error<List<GithubUser>>(RuntimeException(ERROR_NOT_FOUND_USERS))
                .subscribeOn(Schedulers.io())
}