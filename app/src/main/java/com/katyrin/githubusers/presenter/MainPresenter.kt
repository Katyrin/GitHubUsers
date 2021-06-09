package com.katyrin.githubusers.presenter

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.ui.AndroidScreens
import moxy.MvpPresenter

class MainPresenter(
    private val router: Router
) : MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(AndroidScreens.users())
    }

    fun backClicked() {
        router.exit()
    }
}