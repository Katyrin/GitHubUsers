package com.katyrin.githubusers.presenter

import com.github.terrakok.cicerone.Screen
import com.katyrin.githubusers.data.GithubUser

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser): Screen
}