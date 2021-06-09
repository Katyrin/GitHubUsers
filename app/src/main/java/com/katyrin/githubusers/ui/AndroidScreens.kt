package com.katyrin.githubusers.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.presenter.IScreens

object AndroidScreens : IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
}