package com.katyrin.githubusers.ui

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser

object AndroidScreens {
    class UsersScreen : IScreen {
        override fun getFragment(): Screen = FragmentScreen { UsersFragment.newInstance() }
    }

    class UserScreen(private val user: GithubUser) : IScreen {
        override fun getFragment(): Screen = FragmentScreen { UserFragment.newInstance(user) }
    }

    class RepositoryScreen(private val repository: GitHubRepository) : IScreen {
        override fun getFragment(): Screen =
            FragmentScreen { RepositoryFragment.newInstance(repository) }
    }
}