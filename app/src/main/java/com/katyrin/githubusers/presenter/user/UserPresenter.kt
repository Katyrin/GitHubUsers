package com.katyrin.githubusers.presenter.user

import com.github.terrakok.cicerone.Router
import com.katyrin.githubusers.data.GitHubRepository
import com.katyrin.githubusers.data.GithubUser
import com.katyrin.githubusers.repository.IGithubRepositoriesRepo
import com.katyrin.githubusers.ui.AndroidScreens
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import javax.inject.Inject

class UserPresenter(
    private val user: GithubUser
) : MvpPresenter<UserView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var uiScheduler: Scheduler

    @Inject
    lateinit var iGithubRepositoriesRepo: IGithubRepositoriesRepo

    class RepositoriesListPresenter : IRepositoryListPresenter {
        val repositories = mutableListOf<GitHubRepository>()
        override var itemClickListener: ((RepositoryItemView) -> Unit)? = null
        override fun getCount() = repositories.size
        override fun bindView(view: RepositoryItemView) {
            val repository = repositories[view.pos]
            repository.name?.let { view.setName(it) }
        }
    }

    val repositoriesListPresenter = RepositoriesListPresenter()
    private var disposable: CompositeDisposable? = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        user.login?.let { viewState.setLogin(it) }
        loadData()

        repositoriesListPresenter.itemClickListener = { itemView ->
            val repository = repositoriesListPresenter.repositories[itemView.pos]
            router.navigateTo(AndroidScreens.RepositoryScreen(repository).getFragment())
        }
    }

    fun loadData() {
        disposable?.add(
            iGithubRepositoriesRepo.getRepositories(user)
                .observeOn(uiScheduler)
                .subscribe(::updateRepositories) { it.printStackTrace() }
        )
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

    override fun onDestroy() {
        disposable?.clear()
        disposable = null
        super.onDestroy()
    }
}