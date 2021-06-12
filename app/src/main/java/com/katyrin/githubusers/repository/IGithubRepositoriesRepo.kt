package com.katyrin.githubusers.repository

import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepo {
    fun getRepositories(user: GithubUser): Single<List<GitHubRepository>>
}