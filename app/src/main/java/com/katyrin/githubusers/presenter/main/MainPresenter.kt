package com.katyrin.githubusers.presenter.main

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.ui.AndroidScreens
import moxy.MvpPresenter
import javax.inject.Inject

class MainPresenter : MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(AndroidScreens.UsersScreen().getFragment())
    }

    fun backClicked() {
        router.exit()
    }
}
