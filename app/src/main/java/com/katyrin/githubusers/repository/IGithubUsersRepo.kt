package com.katyrin.githubusers.repository

import com.katyrin.githubusers.data.GithubUser
import io.reactivex.rxjava3.core.Single

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}