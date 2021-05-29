package com.katyrin.githubusers.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.katyrin.githubusers.presenter.IScreens

class AndroidScreens: IScreens {
    override fun users() = FragmentScreen { UsersFragment.newInstance() }
}