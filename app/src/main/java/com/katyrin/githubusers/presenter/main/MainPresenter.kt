package com.katyrin.githubusers.presenter.main

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.ui.AndroidScreens
import moxy.MvpPresenter

class MainPresenter(
    private val router: Router
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(AndroidScreens.UsersScreen().getFragment())
    }

    fun backClicked() {
        router.exit()
    }
}