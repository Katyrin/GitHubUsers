package com.katyrin.githubusers.repository

import com.katyrin.githubusers.api.IDataSource
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.utils.ERROR_REPO_LIST
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(val api: IDataSource) : IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser): Single<List<GitHubRepository>> =
        user.reposUrl?.let { url ->
            api.getRepositories(url)
                .subscribeOn(Schedulers.io())
        } ?: Single
            .error<List<GitHubRepository>>(RuntimeException(ERROR_REPO_LIST))
            .subscribeOn(Schedulers.io())
}