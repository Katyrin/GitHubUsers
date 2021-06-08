package com.katyrin.githubusers.presenter.user

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.repository.IGithubRepositoriesRepo
import com.katyrin.githubusers.ui.AndroidScreens
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter

class UserPresenter(
    private val router: Router,
    private val user: GithubUser,
    private val repositoriesRepo: IGithubRepositoriesRepo,
    private val uiScheduler: Scheduler
) : MvpPresenter<UserView>() {

    class RepositoriesListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GitHubRepository>()
        override var itemClickListener: ((RepositoryItemView) -> Unit)? = null
        override fun getCount() = repositories.size
        override fun bindView(view: RepositoryItemView) {
            val repository = repositories[view.pos]
            repository.name?.let { view.setName(it) }
        }
    }

    private val repositoriesListPresenter = RepositoriesListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        repositoriesListPresenter.itemClickListener = { itemView ->
            val repository = repositoriesListPresenter.repositories[itemView.pos]
            router.navigateTo(AndroidScreens.RepositoryScreen(repository).getFragment())
        }
    }

    fun loadData() {
        repositoriesRepo.getRepositories(user)
            .observeOn(uiScheduler)
            .subscribe(::updateRepositories) { it.printStackTrace() }
    }

    private fun updateRepositories(repositories: List<GitHubRepository>) {
        repositoriesListPresenter.repositories.clear()
        repositoriesListPresenter.repositories.addAll(repositories)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}