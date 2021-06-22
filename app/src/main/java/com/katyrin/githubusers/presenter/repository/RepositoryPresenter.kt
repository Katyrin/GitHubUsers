package com.katyrin.githubusers.presenter.repository

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GitHubRepository
import moxy.MvpPresenter
import javax.inject.Inject

class RepositoryPresenter(
    private val gitHubRepository: GitHubRepository
) : MvpPresenter<RepositoryView>() {

    @Inject
    lateinit var router: Router

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