package com.katyrin.githubusers.presenter

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GithubUser
import moxy.MvpPresenter

class UserPresenter(
    private val router: Router,
    private val user: GithubUser
) : MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setLogin(user.login)
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}