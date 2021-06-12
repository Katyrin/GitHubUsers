package com.katyrin.githubusers.data.cache

import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface GithubRepositoriesCache {
    fun getUserRepos(user: GithubUser): Single<List<GitHubRepository>>
    fun putUserRepos(user: GithubUser, repos: List<GitHubRepository>): Completable
}