package com.katyrin.githubusers.ui

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.presenter.IScreen

object AndroidScreens {
    class UsersScreen : IScreen {
        override fun getFragment(): Screen = FragmentScreen { UsersFragment.newInstance() }
    }

    class UserScreen(private val user: GithubUser) : IScreen {
        override fun getFragment(): Screen = FragmentScreen { UserFragment.newInstance(user) }
    }
}