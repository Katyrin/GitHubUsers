package com.katyrin.githubusers.presenter.repository

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GitHubRepository
import moxy.MvpPresenter

class RepositoryPresenter(
    private val router: Router,
    private val gitHubRepository: GitHubRepository
) : MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        viewState.setId(gitHubRepository.id ?: "")
        viewState.setTitle(gitHubRepository.name ?: "")
        viewState.setForksCount((gitHubRepository.forksCount ?: 0).toString())
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}