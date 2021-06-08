package com.katyrin.githubusers.api

import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface IDataSource {
    @GET("/users")
    fun getUsers(): Single<List<GithubUser>>

    @GET
    fun getRepositories(@Url url: String): Single<List<GitHubRepository>>
}